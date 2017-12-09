package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer>{
}
