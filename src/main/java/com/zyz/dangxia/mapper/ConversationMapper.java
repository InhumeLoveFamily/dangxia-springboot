package com.zyz.dangxia.mapper;

import com.zyz.dangxia.common.converstion.ConversationDto;
import com.zyz.dangxia.common.converstion.ConversationWithMsgListDto;
import com.zyz.dangxia.model.ConversationDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConversationDO record);

    int insertAndGetId(ConversationDO record);

    int insertSelective(ConversationDO record);

    ConversationDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConversationDO record);

    int updateByPrimaryKey(ConversationDO record);

    ConversationDO getByInitiatorIdAndTaskId(@Param("initiatorId") int initiatorId,@Param("taskId") int taskId);


    List<ConversationDO> listAboutSomeone(int userId);

    int getIdByInitiatorIdAndTaskId(@Param("initiatorId") int initiatorId,@Param("taskId") int taskId);

    ConversationDto getDtoByPrimaryKey(int conversationId);

    ConversationWithMsgListDto getConversationWithMsgListById(int conversationId);
}