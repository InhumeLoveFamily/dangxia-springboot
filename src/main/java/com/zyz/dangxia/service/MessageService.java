package com.zyz.dangxia.service;

import java.util.Date;

public interface MessageService {
    int delete(int msgId);
    int read(int msgId);
    int push(int conversationId,int sender,Date date,int type,String content,int status);
}
