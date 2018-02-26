package com.zyz.dangxia.service;

import com.zyz.dangxia.dto.OrderDto;

import java.util.Date;
import java.util.List;

public interface OrderService {
    int add(int taskId,int executorId,int status);
    int delete(int orderId);
    OrderDto get(int orderId);

    /**
     * 根据任务查询对应的订单
     * @param taskId
     * @return
     */
    OrderDto getByTaskId(int taskId);

    /**
     * 查询某位客户有关的订单
     * @param userId
     * @return
     */
    List<OrderDto> getAboutMe(int userId);

    int finish(int order,Date finishDate);
}
