package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.bigdata.PriceSectionUtil;
import com.zyz.dangxia.bigdata.Raw2HandledDataUtil;
import com.zyz.dangxia.common.order.OrderDto;
import com.zyz.dangxia.mapper.HandledDataMapper;
import com.zyz.dangxia.mapper.OrderMapper;
import com.zyz.dangxia.mapper.TaskMapper;
import com.zyz.dangxia.mapper.UserMapper;
import com.zyz.dangxia.model.HandledDataDO;
import com.zyz.dangxia.model.OrderDO;
import com.zyz.dangxia.model.TaskDO;
import com.zyz.dangxia.model.UserDO;
import com.zyz.dangxia.service.ConversationService;
import com.zyz.dangxia.service.MessageService;
import com.zyz.dangxia.service.OrderService;
import com.zyz.dangxia.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ConversationService conversationService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TaskService taskService;

    @Autowired
    HandledDataMapper handledDataMapper;

    @Autowired
    Raw2HandledDataUtil raw2HandledDataUtil;


    @Override
    @Transactional
    // 创建订单->在task表中保存订单号->新建对话->发送信息，并更新对话中的lastwords
    public int add(int taskId, int executorId, int status) {
        //先找一下有没有相关order
        OrderDO order = new OrderDO();
        order.setExecutorId(executorId);
        order.setOrderDate(new Date());
        order.setStatus(0);
        order.setFinishDate(new Date());
        orderMapper.insertAndGetId(order);

        TaskDO task = taskMapper.selectByPrimaryKey(taskId);
        //检查任务是否存在，并且没有被接单
        if (task == null) {
            throw new RuntimeException();
        }
        if (task.getOrderId() != -1) {
            throw new RuntimeException();
        }
        task.setOrderId(order.getId());
        taskMapper.updateByPrimaryKey(task);
        //向接单方发送一条接单成功的消息
        //发起对话
        return conversationService.initiateConversation(executorId, taskId);

    }

    @Override
    public int delete(int orderId) {
        return 0;
    }

    @Override
    public OrderDto get(int orderId) {
        return translate(orderMapper.selectByPrimaryKey(orderId));
    }

    private OrderDto translate(OrderDO order) {
        if (order == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);
        orderDto.setFinishDate(format.format(order.getFinishDate()));
        orderDto.setOrderDate(format.format(order.getOrderDate()));
        orderDto.setTaskDto(taskService.getByOrder(order.getId()));
        orderDto.setExecutorName(userMapper.getName(order.getExecutorId()));
        return orderDto;
    }

    @Override
    public OrderDto getByTaskId(int taskId) {
        return translate(orderMapper.getByTaskId(taskId));

    }

    @Override
    public List<OrderDto> getAboutMe(int userId) {
        return null;
    }

    @Override
    @Transactional
    public int cancelAuthor(int userId, int orderId) {
        UserDO user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return 0;
        }

        TaskDO task = taskMapper.getByOrderId(orderId);
        if (task.getOrderId() == -1) {
            return 1;
        }
        task.setOrderId(-1);
        taskMapper.updateByPrimaryKey(task);
        return orderMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    @Transactional
    public int finish(int orderId, Date finishDate) {
        OrderDO order = orderMapper.selectByPrimaryKey(orderId);
        if (orderId == -1 || order == null) {
            throw new RuntimeException();
        }
        order.setStatus(1);
        order.setFinishDate(finishDate);
        orderMapper.updateByPrimaryKey(order);
        TaskDO task = taskMapper.getByOrderId(orderId);
        //在样本数据库中写入数据
        HandledDataDO data = raw2HandledDataUtil.getHandledData(task.getClassId(),
                task.getContent(), task.getPublishDate(), PriceSectionUtil.getP(task.getPrice()));

        return handledDataMapper.insert(data);
    }
}
