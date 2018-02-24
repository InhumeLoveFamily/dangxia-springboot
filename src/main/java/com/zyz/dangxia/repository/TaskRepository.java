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

}
