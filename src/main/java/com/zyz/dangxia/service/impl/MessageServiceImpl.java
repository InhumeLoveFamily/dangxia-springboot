package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.MessageDto;
import com.zyz.dangxia.entity.Message;
import com.zyz.dangxia.repository.MessageRepository;
import com.zyz.dangxia.service.MessageService;
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


    @Autowired
    private MessageRepository messageRepository;

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
    public int add(String content, int senderId, int receiverId, Date date) {
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

    private MessageDto translate(Message message) {
        if (message == null) {
            return null;
        }
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        messageDto.setDate(format.format(message.getDate()));
        return messageDto;
    }

    private List<MessageDto> translate(List<Message> messageList) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messageList) {
            messageDtos.add(translate(message));
        }
        return messageDtos;
    }
}
