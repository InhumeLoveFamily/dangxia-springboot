package com.zyz.dangxia.mapper;

import com.zyz.dangxia.model.OrderDO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);

    int insertAndGetId(OrderDO record);

    OrderDO getByTaskId(int taskId);
}