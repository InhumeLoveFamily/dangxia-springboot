package com.zyz.dangxia.mapper;

import com.zyz.dangxia.model.TaskClassDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskClassDO record);

    int insertSelective(TaskClassDO record);

    TaskClassDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskClassDO record);

    int updateByPrimaryKey(TaskClassDO record);

    List<TaskClassDO> listUseful();
}