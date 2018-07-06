package com.zyz.dangxia.mapper;

import com.zyz.dangxia.model.MessageDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageDO record);

    int insertSelective(MessageDO record);

    MessageDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageDO record);

    int updateByPrimaryKey(MessageDO record);

    int read(int msgId);

    List<MessageDO> listVisiableAsc(int conversationId);

    List<MessageDO> listVisiableAfterSometimeAsc(@Param("conversationId") int conversationId, @Param("beginDate") Date beginDate);
}