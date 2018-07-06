package com.zyz.dangxia.mapper;

import com.zyz.dangxia.model.PictureDO;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureMapper {
    int deleteByPrimaryKey(Integer id);

    int insertAndGetId(PictureDO record);

    int insert(PictureDO record);

    int insertSelective(PictureDO record);

    PictureDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PictureDO record);

    int updateByPrimaryKeyWithBLOBs(PictureDO record);

    int updateByPrimaryKey(PictureDO record);

    String getPathByUserId(Integer userId);
}