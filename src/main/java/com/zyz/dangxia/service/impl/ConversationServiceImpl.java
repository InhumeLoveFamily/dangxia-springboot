package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.ConversationDto;
import com.zyz.dangxia.dto.MessageDto;
import com.zyz.dangxia.dto.TaskDto;
import com.zyz.dangxia.mapper.ConversationMapper;
import com.zyz.dangxia.mapper.MessageMapper;
import com.zyz.dangxia.mapper.TaskMapper;
import com.zyz.dangxia.mapper.UserMapper;
import com.zyz.dangxia.model.ConversationDO;
import com.zyz.dangxia.model.MessageDO;
import com.zyz.dangxia.model.TaskDO;
import com.zyz.dangxia.service.ConversationService;
import com.zyz.dangxia.service.MessageService;
import com.zyz.dangxia.service.TaskService;
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
public class ConversationServiceImpl implements ConversationService {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TaskService taskService;

    @Override
    public List<ConversationDto> getConversation(int userId) {

        return translate1(conversationMapper.listAboutSomeone(userId));
    }

    @Override
    public int initiateConversation(int initiatorId, int taskId) {
        TaskDO task = taskMapper.selectByPrimaryKey(taskId);
        //先查找有没有之前创建好的会话
        ConversationDO conversation = conversationMapper.getByInitiatorIdAndTaskId(initiatorId, taskId);
        if (conversation == null) {
            //如果没有则新建
            conversation = new ConversationDO();
            conversation.setInitDate(new Date());
            conversation.setTaskId(taskId);
            conversation.setInitiatorId(initiatorId);
            conversation.setLastDate(new Date());
            int result = conversationMapper.insert(conversation);
            if (result < 1) {
                throw new RuntimeException();
            }
            messageService.push(conversation.getId(), initiatorId, new Date(), 0,
                    task.getRequireVerify() == 1 ?
                            "您好，也许我能提供帮助。" : "您好，由我来为您服务。", 0);
        } else {
            // 否则就是接单成功，需要由任务发布者发一条消息给接单者
            messageService.push(conversation.getId(), task.getPublisher(), new Date(), -1,
                    MessageDto.ORDER_CREATED, 0);
        }
        return conversation.getId();
    }

    @Override
    public List<MessageDto> getMsgList(int conId) {
        return translate(messageMapper.listVisiableAsc(conId));
    }

    @Override
    public List<MessageDto> getMsgList(int conId, Date beginDate) {
        return translate(messageMapper.listVisiableAfterSometimeAsc(conId, beginDate));
    }

    @Override
    public ConversationDto get(int id) {
        return translate(conversationMapper.selectByPrimaryKey(id));
    }

    private MessageDto translate(MessageDO message) {
        if (message == null) {
            return null;
        }
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        messageDto.setMsgId(message.getId());
        messageDto.setDate(format.format(message.getDate()));
        messageDto.setSenderName(userMapper.getName(message.getSender()));
        return messageDto;
    }

    private List<MessageDto> translate(List<MessageDO> messageList) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for (MessageDO message : messageList) {
            messageDtos.add(translate(message));
        }
        return messageDtos;
    }

    private List<ConversationDto> translate1(List<ConversationDO> conversations) {
        List<ConversationDto> conversationDtos = new ArrayList<>();
        for (ConversationDO conversation : conversations) {
            conversationDtos.add(translate(conversation));
        }
        return conversationDtos;
    }

    private ConversationDto translate(ConversationDO conversation) {
        if (conversation == null) {
            return null;
        }
        ConversationDto conversationDto = new ConversationDto();
        BeanUtils.copyProperties(conversation, conversationDto);
        conversationDto.setInitDate(format.format(conversation.getInitDate()));
        conversationDto.setLastDate(format.format(conversation.getLastDate()));
        conversationDto.setInitiatorName(userMapper.getName(conversationDto.getInitiatorId()));
        TaskDto task = taskService.get(conversation.getTaskId());
        conversationDto.setTask(task);
        conversationDto.setPublisherName(task.getPublisherName());
        return conversationDto;
    }

}
