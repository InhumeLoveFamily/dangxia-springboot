package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.common.converstion.MessageDto;
import com.zyz.dangxia.mapper.ConversationMapper;
import com.zyz.dangxia.mapper.MessageMapper;
import com.zyz.dangxia.mapper.TaskMapper;
import com.zyz.dangxia.mapper.UserMapper;
import com.zyz.dangxia.model.ConversationDO;
import com.zyz.dangxia.model.MessageDO;
import com.zyz.dangxia.util.mqtt.MqttManager;
import com.zyz.dangxia.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserMapper userMapper;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    ConversationMapper conversationMapper;

    @Autowired
    private MessageMapper messageMapper;


    @Override
    public int delete(int msgId) {
        return 0;
    }

    @Override
    public int read(int msgId) {
        return messageMapper.read(msgId);
    }

    @Override
    public int push(int conversationId, int sender, Date date, int type, String content, int status) {
        MessageDO message = new MessageDO();
        message.setConversationId(conversationId);
        message.setStatus(status);
        message.setContent(content);
        message.setDate(date);
        message.setSender(sender);
        message.setType(type);
        messageMapper.insert(message);
        logger.info("已成功保存信息");
        ConversationDO conversation = conversationMapper.selectByPrimaryKey(conversationId);
        if (!content.contains(MessageDto.ORDER_CREATED) && !content.contains(MessageDto.PRICE_CHANGED)) {
            conversation.setLastWords(content);
            conversation.setLastDate(date);
            conversationMapper.updateByPrimaryKey(conversation);
        }
        //找到消息的接收者，要么是会话发起者(conversation.initiatorId)，要么是任务发布者task.publisherId
        int receiverId = conversation.getInitiatorId() == sender ?
                taskMapper.getPublisher(conversation.getTaskId()) : conversation.getInitiatorId();
        logger.info("接收者id={}", receiverId);
        MqttManager.getInstance().publishMsg(translate(message), receiverId);
        return 1;
    }

    private MessageDto translate(MessageDO message) {
        if (message == null) {
            return null;
        }
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        messageDto.setDate(format.format(message.getDate()));
        messageDto.setSenderName(userMapper.getName(message.getSender()));
        return messageDto;
    }
}
