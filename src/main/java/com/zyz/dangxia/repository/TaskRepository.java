package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer>{
    List<Task> findByExecutorIsOrderByPublishDateDesc(int id);
    List<Task> findByExecutorIsAndTypeIsOrderByPublishDateDesc(int id,int type);
    List<Task> findByPublisherIsOrderByPublishDateDesc(int id);
}
