package com.zyz.dangxia.mapper;

import com.zyz.dangxia.model.KeywordDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeywordDO record);

    int insertSelective(KeywordDO record);

    KeywordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KeywordDO record);

    int updateByPrimaryKey(KeywordDO record);

    List<KeywordDO> listByClassId(int classId);
}