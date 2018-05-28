package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.bigdata.PriceSectionUtil;
import com.zyz.dangxia.bigdata.Raw2HandledDataUtil;
import com.zyz.dangxia.dto.OrderDto;
import com.zyz.dangxia.dto.PriceSection;
import com.zyz.dangxia.entity.HandledData;
import com.zyz.dangxia.entity.Order;
import com.zyz.dangxia.entity.Task;
import com.zyz.dangxia.entity.User;
import com.zyz.dangxia.repository.HandledDataRepository;
import com.zyz.dangxia.repository.OrderRepository;
import com.zyz.dangxia.repository.TaskRepository;
import com.zyz.dangxia.repository.UserRepository;
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
    OrderRepository orderRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ConversationService conversationService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    HandledDataRepository handledDataRepository;

    @Autowired
    Raw2HandledDataUtil raw2HandledDataUtil;


    @Override
    @Transactional
    //创建订单->在task表中保存订单号->新建对话->发送信息，并更新对'''话中的lastwords
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
        //检查任务是否存在，并且没有被接单
        if (task == null) {
            throw new RuntimeException();
        }
        if (task.getOrderId() != -1) {
            throw new RuntimeException();
        }
        task.setOrderId(order.getId());
        taskRepository.saveAndFlush(task);
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
        return translate(orderRepository.findById(orderId));
    }

    private OrderDto translate(Order order) {
        if (order == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);
        orderDto.setFinishDate(format.format(order.getFinishDate()));
        orderDto.setOrderDate(format.format(order.getOrderDate()));
        orderDto.setTaskDto(taskService.getByOrder(order.getId()));
        orderDto.setExecutorName(userRepository.findById(order.getExecutorId()).getName());
        return orderDto;
    }

    @Override
    public OrderDto getByTaskId(int taskId) {
        return translate(orderRepository.findById(taskRepository.findById(taskId).getOrderId()));

    }

    @Override
    public List<OrderDto> getAboutMe(int userId) {
        return null;
    }

    @Override
    @Transactional
    public int cancelAuthor(int userId, int orderId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return 0;
        }

        Task task = taskRepository.findByOrderId(orderId);
        if (task.getOrderId() == -1) {
            return 1;
        }
        task.setOrderId(-1);
        taskRepository.saveAndFlush(task);
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new RuntimeException();
        }
        orderRepository.delete(orderId);
        return 1;
    }

    @Override
    @Transactional
    public int finish(int orderId, Date finishDate) {
        Order order = orderRepository.findById(orderId);
        if (orderId == -1 || order == null) {
            throw new RuntimeException();
        }
        order.setStatus(1);
        order.setFinishDate(finishDate);
        orderRepository.saveAndFlush(order);
        Task task = taskRepository.findByOrderId(orderId);
        //在样本数据库中写入数据
        HandledData data = raw2HandledDataUtil.getHandledData(task.getClassId(),
                task.getContent(), task.getPublishDate(), PriceSectionUtil.getP(task.getPrice()));
        handledDataRepository.saveAndFlush(data);
        return 1;
    }
}
