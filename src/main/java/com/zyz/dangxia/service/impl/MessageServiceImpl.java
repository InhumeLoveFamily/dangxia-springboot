package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.MessageDto;
import com.zyz.dangxia.entity.Conversation;
import com.zyz.dangxia.entity.Message;
import com.zyz.dangxia.mqtt.MqttManager;
import com.zyz.dangxia.repository.ConversationRepository;
import com.zyz.dangxia.repository.MessageRepository;
import com.zyz.dangxia.repository.TaskRepository;
import com.zyz.dangxia.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<MessageDto> getMsgAboutMe(int userId) {
//        return translate(
//                messageRepository.findBySenderIdIsOrReceiverIdIsOrderByDateAsc(userId, userId));
        return null;
    }


    @Override
    public int delete(int msgId) {
        return 0;
    }

    @Override
    public int read(int msgId) {
        Message message = messageRepository.findOne(msgId);
        if(message == null) {
            return -1;
        }
        message.setStatus(1);
        messageRepository.saveAndFlush(message);
        message = messageRepository.findOne(msgId);
        if(message.getStatus() == 1) {
            return 1;
        }
        return 0;
    }

    @Override
    public int push(int conversationId, int sender, Date date, int type, String content,int status) {
        Message message = new Message();
        message.setConversationId(conversationId);
        message.setStatus(status);
        message.setContent(content);
        message.setDate(date);
        message.setSender(sender);
        message.setType(type);
        messageRepository.saveAndFlush(message);
        logger.info("已成功保存信息");

        Conversation conversation = conversationRepository.findById(conversationId);
        conversation.setLastWords(content);
        conversation.setLastDate(date);
        conversationRepository.saveAndFlush(conversation);

        //找到消息的接收者，要么是会话发起者(conversation.initiatorId)，要么是任务发布者task.publisherId
        int receiverId = conversation.getInitiatorId() == sender ?
                taskRepository.findTaskPublisherById(conversation.getTaskId()) : conversation.getInitiatorId();
        logger.info("接收者id={}", receiverId);
        MqttManager.getInstance().publishMsg(translate(message), receiverId);
        return 1;
    }

    public MessageDto translate(Message message) {
        if (message == null) {
            return null;
        }
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        messageDto.setDate(format.format(message.getDate()));
        return messageDto;
    }

    public List<MessageDto> translate(List<Message> messageList) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messageList) {
            messageDtos.add(translate(message));
        }
        return messageDtos;
    }
}
