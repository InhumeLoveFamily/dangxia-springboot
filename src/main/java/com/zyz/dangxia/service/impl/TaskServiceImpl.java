package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.bigdata.Raw2HandledDataUtil;
import com.zyz.dangxia.bigdata.HandledDataList;
import com.zyz.dangxia.bigdata.PriceSectionUtil;
import com.zyz.dangxia.bigdata.TaskClassList;
import com.zyz.dangxia.dto.MessageDto;
import com.zyz.dangxia.dto.PriceSection;
import com.zyz.dangxia.dto.TaskClassDto;
import com.zyz.dangxia.dto.TaskDto;
import com.zyz.dangxia.mapper.ConversationMapper;
import com.zyz.dangxia.mapper.EvaluationCacheRepository;
import com.zyz.dangxia.mapper.TaskMapper;
import com.zyz.dangxia.mapper.UserMapper;
import com.zyz.dangxia.model.HandledDataDO;
import com.zyz.dangxia.model.TaskClassDO;
import com.zyz.dangxia.model.TaskDO;
import com.zyz.dangxia.service.MessageService;
import com.zyz.dangxia.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.zyz.dangxia.base.DistanceUtil.km;

@Service
public class TaskServiceImpl implements TaskService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ConversationMapper conversationMapper;


    @Override
    public List<TaskDto> getAll() {
        return translate(taskMapper.list());
    }

    @Override
    public TaskDto get(int taskId) {
        return translate(taskMapper.selectByPrimaryKey(taskId));
    }

    @Override
    public TaskDto getByOrder(int orderId) {
        return translate(taskMapper.getByOrderId(orderId));
    }

    @Override
    public List<TaskDto> getNearby(double latitude, double longitude, double radius) {
        return getNearby(latitude,longitude,radius,1);
    }

    @Override
    public List<TaskDto> getAccepted(int userId) {
        return translate(taskMapper.listToDo(userId));
    }

    @Override
    public List<TaskDto> getPublished(int userId) {
        return translate(taskMapper.listPublished(userId));

    }

    @Override
    public List<TaskDto> getNearbyQuick(double latitude, double longitude, double radius) {
        return getNearby(latitude,longitude,radius,0);
    }

    private List<TaskDto> getNearby(double latitude, double longitude, double radius,int taskType) {
        List<TaskDto> all = translate(taskMapper.listAcceptableDesc(taskType));
        List<TaskDto> result = new ArrayList<>();
        for (TaskDto taskDto : all) {
            //如果距离小于指定范围
            if (km(taskDto.getLatitude(), taskDto.getLongitude(), latitude, longitude) < radius) {
                result.add(taskDto);
            }
        }
        return result;
    }

    @Override
    public int add(int publisher, int type, Date publishDate, Date endDate, String content, int requireVerify,
                   String location, double latitude, double longitude, double price, int classId) {
        TaskDO task = new TaskDO();
        task.setContent(content);
        task.setEndDate(endDate);
        task.setOrderId(-1);
        task.setLatitude(latitude);
        task.setLongitude(longitude);
        task.setLocation(location);
        task.setPrice(price);
        task.setPublishDate(publishDate);
        task.setPublisher(publisher);
        task.setType(type);
        task.setClassId(classId);
        task.setRequireVerify(requireVerify);
        return taskMapper.insert(task);
    }

    @Override
    public int delete(int taskId) {
        return taskMapper.deleteByPrimaryKey(taskId);
    }

    @Override
    public int appoint(int taskId, int userId) {
        // 此业务已交由ConversationService处理
        return 1;
    }

    @Override
    public int changePrice(double newPrice, int taskId, int receiverId) {
        if (newPrice < 0) return -1;
        TaskDO task = new TaskDO();
        task.setPrice(newPrice);
        task.setId(taskId);
        taskMapper.updateByPrimaryKeySelective(task);
        // 修改成功后需要通知接单者
        messageService.push(conversationMapper.getIdByInitiatorIdAndTaskId(receiverId,taskId),
                task.getPublisher(), new Date(), -1, MessageDto.PRICE_CHANGED, 0);
        return 1;
    }

    @Autowired
    TaskClassList taskClassList;

    @Override
    public List<TaskClassDto> getClasses() {
        return translate1(taskClassList.getList());
    }

    @Autowired
    private Raw2HandledDataUtil raw2HandledDataUtil;

    @Autowired
    HandledDataList handledDataList;

    @Autowired
    EvaluationCacheRepository evaluationCacheRepository;

    @Override
    public PriceSection getPriceSection(int classId, String taskContent, Date date) {
        HandledDataDO handledData = raw2HandledDataUtil.getHandledData(classId, taskContent, date, 0);
        PriceSection section = evaluationCacheRepository
                .getPriceSection(handledData.getKey());
        if (section != null) {
            logger.info("从redis缓存中拿到了结果。");
            return section;
        }

        logger.info("被处理后的请求为：{}", handledData.toString());
        //进行KNN算法
        //获取样本数据
        double[][] sample = handledDataList.getDataWithDistanceList(classId);
        //计算距离
        for (int i = 0; i < sample.length; i++) {
            sample[i][6] = Math.sqrt(
                    Math.pow((handledData.getC0() - sample[i][0]), 2) +
                            Math.pow((handledData.getC1() - sample[i][1]), 2) +
                            Math.pow((handledData.getC2() - sample[i][2]), 2) +
                            Math.pow((handledData.getC3() - sample[i][3]), 2) +
                            Math.pow((1 / 3) * ((handledData.getT() - sample[i][4])), 2)
            );
            logger.info("计算出的距离为{}", sample[i][6]);
        }
        //按照距离进行排序
        Arrays.sort(sample, (o1, o2) -> (int) (o1[6] - o2[6]));
        //取出前K=6个,统计各价格类别出现次数
        int[] counts = new int[8];
        for (int i = 0; i < 6 && i < sample.length; i++) {
            logger.info("第{}个数据是属于{}类别的", i + 1, sample[i][5]);
            counts[(int) sample[i][5]]++;
        }
        //找到出现次数最多的类别
        int p = 0;
        int max = 0;
        for (int i = 1; i < 8; i++) {
            if (counts[i] > max) {
                max = counts[i];
                p = i;
            }
        }
        logger.info("最终得出的p为{}", p);
        section = PriceSectionUtil.getSection(p);
        logger.info("存入redis");
        evaluationCacheRepository.putPriceSection(handledData.getKey(), section);
        return section;
    }

    @Override
    public double getPrice(int taskId) {
        return taskMapper.getPrice(taskId);
    }

    @Override
    public List<TaskDto> getServed(int userId) {
        return translate(taskMapper.listServed(userId));
    }

    @Override
    public List<TaskDto> getBeServed(int userId) {
        return translate(taskMapper.listBeServed(userId));
    }

    private TaskClassDto translate(TaskClassDO taskClass) {
        TaskClassDto dto = new TaskClassDto();
        BeanUtils.copyProperties(taskClass, dto);
        return dto;
    }

    private List<TaskClassDto> translate1(List<TaskClassDO> list) {
        List<TaskClassDto> result = new ArrayList<>();
        for (TaskClassDO taskClass : list) {
            result.add(translate(taskClass));
        }
        return result;
    }

    private TaskDto translate(TaskDO task) {
        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(task, taskDto);
        taskDto.setPublishDate(format.format(task.getPublishDate()));
        taskDto.setEndDate(format.format(task.getEndDate()));
        taskDto.setPublisherName(userMapper.getName(task.getPublisher()));
        return taskDto;
    }

    private List<TaskDto> translate(List<TaskDO> tasks) {
        List<TaskDto> taskDtos = new ArrayList<>();
        for (TaskDO task : tasks) {
            taskDtos.add(translate(task));
        }
        return taskDtos;
    }

}
