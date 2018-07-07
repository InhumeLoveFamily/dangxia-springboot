package com.zyz.dangxia.service;

import com.zyz.dangxia.common.converstion.ConversationDto;
import com.zyz.dangxia.common.converstion.MessageDto;

import java.util.Date;
import java.util.List;

public interface ConversationService {
    /**
     * 获取与某人有关的对话
     * @param userId 查询者id
     * @return
     */
    List<ConversationDto> getConversation(int userId);

    /**
     * 发起对话
     * @param initiatorId 发起者id
     * @param taskId 对话涉及的任务
     * @return
     */
    int initiateConversation(int initiatorId, int taskId);

    /**
     * 获取会话里的聊天记录
     * @param conId
     * @return
     */
    List<MessageDto> getMsgList(int conId);

    List<MessageDto> getMsgList(int conId, Date beginDate);

    ConversationDto get(int id);
}
