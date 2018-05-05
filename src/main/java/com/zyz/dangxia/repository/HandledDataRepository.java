package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.HandledData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HandledDataRepository extends JpaRepository<HandledData, Integer> {
    List<HandledData> findByClassId(int classId);
}
