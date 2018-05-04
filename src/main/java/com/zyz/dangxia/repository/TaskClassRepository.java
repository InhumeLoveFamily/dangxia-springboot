package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.TaskClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskClassRepository extends JpaRepository<TaskClass,Integer> {
    @Query("from TaskClass where id <> -1")
    List<TaskClass> findUserful();
}
