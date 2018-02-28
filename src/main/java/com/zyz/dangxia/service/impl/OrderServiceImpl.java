package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.OrderDto;
import com.zyz.dangxia.entity.Order;
import com.zyz.dangxia.entity.Task;
import com.zyz.dangxia.entity.User;
import com.zyz.dangxia.repository.OrderRepository;
import com.zyz.dangxia.repository.TaskRepository;
import com.zyz.dangxia.repository.UserRepository;
import com.zyz.dangxia.service.ConversationService;
import com.zyz.dangxia.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ConversationService conversationService;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    //创建订单->在task表中保存订单号->新建对话->发送信息，并更新对话中的lastwords
    public int add(int taskId, int executorId, int status) {
        Order order = new Order();
        order.setExecutorId(executorId);
        order.setOrderDate(new Date());
        order.setStatus(0);
        order.setFinishDate(new Date());
        order = orderRepository.saveAndFlush(order);
        if (order == null) {
            throw new RuntimeException();
        }
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            throw new RuntimeException();
        }
        if (task.getOrderId() != -1) {
            throw new RuntimeException();
        }
        task.setOrderId(order.getId());
        taskRepository.saveAndFlush(task);
        //发起对话
        return conversationService.initiateConversation(executorId,taskId);

    }

    @Override
    public int delete(int orderId) {
        return 0;
    }

    @Override
    public OrderDto get(int orderId) {
        return null;
    }

    @Override
    public OrderDto getByTaskId(int taskId) {
        return null;
    }

    @Override
    public List<OrderDto> getAboutMe(int userId) {
        return null;
    }

    @Override
    @Transactional
    public int cancelAuthor(int userId, String password, int orderId) {
        User user = userRepository.findById(userId);
        if(user == null) {
            return 0;
        }
        if(!user.getPassword().equals(password)) {
            return -1;
        }
        Task task = taskRepository.findByOrderId(orderId);
        if(task.getOrderId()==-1) {
            return 1;
        }
        task.setOrderId(-1);
        taskRepository.saveAndFlush(task);
        Order order = orderRepository.findById(orderId);
        if(order == null) {
            throw new RuntimeException();
        }
        orderRepository.delete(orderId);
        return 1;
    }

    @Override
    public int finish(int order, Date finishDate) {
        return 0;
    }
}
