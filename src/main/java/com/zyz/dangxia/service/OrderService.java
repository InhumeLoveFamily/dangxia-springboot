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

    /**
     * 取消任务的授权
     * @param userId
     * @param password
     * @param orderId
     * @return
     */
    int cancelAuthor(int userId,String password,int orderId);

    int finish(int order,Date finishDate);
}
