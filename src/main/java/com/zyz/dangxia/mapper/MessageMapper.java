package com.zyz.dangxia.mapper;

import com.zyz.dangxia.model.MessageDO;
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

    List<MessageDO> listByConversationIdAsc(int conversationId);

    List<MessageDO> listByConversationIdAfterSometimeAsc(int conId, Date beginDate);
}