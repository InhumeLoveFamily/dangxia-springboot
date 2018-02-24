package com.zyz.dangxia.service;

import com.zyz.dangxia.dto.MessageDto;

import java.util.Date;
import java.util.List;

public interface MessageService {
    List<MessageDto> getMsgAboutMe(int userId);
    int delete(int msgId);
    int add(String content, int senderId, int receiverId, Date date);
    int read(int msgId);
}
