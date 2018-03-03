package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.TaskDto;
import com.zyz.dangxia.entity.Task;
import com.zyz.dangxia.entity.User;
import com.zyz.dangxia.repository.TaskRepository;
import com.zyz.dangxia.repository.UserRepository;
import com.zyz.dangxia.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.zyz.dangxia.base.DistanceUtil.km;

@Service
public class TaskServiceImpl implements TaskService{

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TaskDto> getAll() {
        return translate(taskRepository.findAll());
    }

    @Override
    public TaskDto get(int taskId) {
        return translate(taskRepository.findById(taskId));
    }

    @Override
    public TaskDto getByOrder(int orderId) {
        return translate(taskRepository.findByOrderId(orderId));
    }

    @Override
    public List<TaskDto> getNearby(double latitude, double longitude, double radius) {
        List<TaskDto> all = translate(taskRepository.findByOrderIdIsAndTypeIsOrderByPublishDateDesc(-1,1));
        List<TaskDto> result = new ArrayList<>();

        for(TaskDto taskDto : all) {
            //如果距离小于指定范围
            if(km(taskDto.getLatitude(),taskDto.getLongitude(),latitude,longitude) < radius) {
                result.add(taskDto);
            }
        }
        return result;
    }

    @Override
    public List<TaskDto> getAccepted(int userId) {
        return translate(taskRepository.findByOrderIdIsOrderByPublishDateDesc(userId));
    }

    @Override
    public List<TaskDto> getPublished(int userId) {
        return translate(taskRepository.findByPublisherIsOrderByPublishDateDesc(userId));

    }

    @Override
    public List<TaskDto> getNearbyQuick(double latitude, double longitude, double radius) {
        List<TaskDto> all = translate(taskRepository.findByOrderIdIsAndTypeIsOrderByPublishDateDesc(-1,0));
        List<TaskDto> result = new ArrayList<>();
        for(TaskDto taskDto : all) {
            //如果距离小于指定范围
            if(km(taskDto.getLatitude(),taskDto.getLongitude(),latitude,longitude) < radius) {
                result.add(taskDto);
            }
        }
        return result;
    }

    @Override
    public int add(int publisher, int type, Date publishDate, Date endDate, String content, int requireVerify, String location, double latitude, double longitude, double price) {
        Task task = new Task();
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
        task.setRequireVerify(requireVerify);
        task = taskRepository.saveAndFlush(task);
        if(task == null) {
            return -1;
        }
        if(task.getPublisher() == publisher) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int delete(int taskId) {
        Task task = taskRepository.findOne(taskId);
        if(task == null) {
            return -1;
        }
        try {
            taskRepository.delete(taskId);
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    @Override
    public int update(Task task) {
        return 0;
    }

    @Override
    public int appoint(int taskId, int userId) {
        Task task = taskRepository.findById(taskId);
        User user = userRepository.findById(userId);
        if(task == null || user == null) {
            return -1;
        }

//        task.setExecutor(userId);
        taskRepository.saveAndFlush(task);
        return 1;
    }

    private TaskDto translate(Task task){
        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(task,taskDto);
        taskDto.setPublishDate(format.format(task.getPublishDate()));
        taskDto.setEndDate(format.format(task.getEndDate()));
        taskDto.setPublisherName(userRepository.findById(task.getPublisher()).getName());
        return taskDto;
    }

    private List<TaskDto> translate(List<Task> tasks) {
        List<TaskDto> taskDtos = new ArrayList<>();
        for(Task task : tasks) {
            taskDtos.add(translate(task));
        }
        return taskDtos;
    }

}
