package com.zyz.dangxia.service;

import com.zyz.dangxia.dto.MessageDto;

import java.util.Date;
import java.util.List;

public interface MessageService {
    List<MessageDto> getMsgAboutMe(int userId);
    int delete(int msgId);
    int read(int msgId);
    int push(int conversationId,int sender,Date date,int type,String content,int status);
}
