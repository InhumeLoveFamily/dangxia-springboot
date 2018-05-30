package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer>{
    List<Task> findByOrderIdIsOrderByPublishDateDesc(int id);
    List<Task> findByOrderIdIsAndTypeIsOrderByPublishDateDesc(int id,int type);
    List<Task> findByPublisherIsOrderByPublishDateDesc(int id);

    @Query("select id from Task where publisher = ?1")
    List<Integer> findTaskIds(int userId);

    Task findById(int taskId);

    Task findByOrderId(int orderId);

    @Query("select publisher from Task where id = ?1")
    int findTaskPublisherById(int taskId);

    @Query("select price from Task where id = ?1")
    double findPriceById(int taskId);

    @Query(nativeQuery = true, value = "select task.class_id,task.content,task.end_date,task.id,task.latitude,task.longitude,task.location,task.order_id,task.price,task.publisher,task.publish_date,task.require_verify,task.type from task LEFT JOIN dx_order ON task.order_id = dx_order.id where dx_order.executor_id = ?1 and dx_order.`status` = '1'")
    List<Task> findServedTasks(int userId);

    @Query(nativeQuery = true, value = "select task.class_id,task.content,task.end_date,task.id,task.latitude,task.longitude,task.location,task.order_id,task.price,task.publisher,task.publish_date,task.require_verify,task.type from task LEFT JOIN dx_order ON task.order_id = dx_order.id where task.publisher = ?1 and dx_order.`status` = '1'")
    List<Task> findBeServedTask(int userId);

}
