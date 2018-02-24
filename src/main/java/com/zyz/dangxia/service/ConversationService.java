package com.zyz.dangxia.service;

import com.zyz.dangxia.dto.ConversationDto;

import java.util.List;

public interface ConversationService {
    /**
     * 获取与某人有关的对话
     * @param userId 查询者id
     * @return
     */
    List<ConversationDto> getconversation(int userId);

    /**
     * 发起对话
     * @param initiatorId 发起者id
     * @param taskId 对话涉及的任务
     * @return
     */
    int initiateconversation(int initiatorId,int taskId);
}
