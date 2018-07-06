package com.zyz.dangxia.mapper;

import com.zyz.dangxia.model.HandledDataDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HandledDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HandledDataDO record);

    int insertSelective(HandledDataDO record);

    HandledDataDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HandledDataDO record);

    int updateByPrimaryKey(HandledDataDO record);

    List<HandledDataDO> listByClassId(int classId);

    List<HandledDataDO> listAll();
}