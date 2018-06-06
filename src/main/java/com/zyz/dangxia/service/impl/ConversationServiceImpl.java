package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.ConversationDto;
import com.zyz.dangxia.dto.MessageDto;
import com.zyz.dangxia.dto.TaskDto;
import com.zyz.dangxia.entity.Conversation;
import com.zyz.dangxia.entity.Message;
import com.zyz.dangxia.entity.Task;
import com.zyz.dangxia.repository.ConversationRepository;
import com.zyz.dangxia.repository.MessageRepository;
import com.zyz.dangxia.repository.TaskRepository;
import com.zyz.dangxia.repository.UserRepository;
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
    private ConversationRepository conversationRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TaskService taskService;

    @Override
    public List<ConversationDto> getConversation(int userId) {
        //找出自己发布的任务相关的会话+自己主动发起的会话
        List<Integer> myTasks = taskRepository.findTaskIds(userId);
//        logger.info();
        List<Conversation> conversations = conversationRepository
                .findByTaskIdInOrInitiatorIdIsOrderByLastDateDesc(myTasks, userId);
        return translate1(conversations);
    }



    @Override
    public int initiateConversation(int initiatorId, int taskId) {
        Task task = taskRepository.findById(taskId);
        //先查找有没有之前创建好的会话
        Conversation conversation = conversationRepository.findByTaskIdAndInitiatorId(taskId,initiatorId);
        if(conversation == null) {
            //如果没有则新建
            conversation = new Conversation();
            conversation.setInitDate(new Date());
            conversation.setTaskId(taskId);
            conversation.setInitiatorId(initiatorId);
            conversation.setLastDate(new Date());
            conversation = conversationRepository.saveAndFlush(conversation);
            if (conversation == null) {
                throw new RuntimeException();
            }
            messageService.push(conversation.getId(), initiatorId, new Date(), 0,
                    task.getRequireVerify() == 1 ?
                            "您好，也许我能提供帮助。" : "您好，由我来为您服务。",0);
        } else {
            // 否则就是接单成功，需要由任务发布者发一条消息给接单者
            messageService.push(conversation.getId(), task.getPublisher(), new Date(), -1,
                    MessageDto.ORDER_CREATED, 0);
        }
        return conversation.getId();
    }

    @Override
    public List<MessageDto> getMsgList(int conId) {
        return translate(messageRepository.findByConversationIdOrderByDateAsc(conId));
    }

    @Override
    public List<MessageDto> getMsgList(int conId, Date beginDate) {
        return translate(messageRepository.get(conId, beginDate));
    }

    @Override
    public ConversationDto get(int id) {
        return translate(conversationRepository.findById(id));
    }

    private MessageDto translate(Message message) {
        if (message == null) {
            return null;
        }
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        messageDto.setMsgId(message.getId());
        messageDto.setDate(format.format(message.getDate()));
        messageDto.setSenderName(userRepository.findName(message.getSender()));
        return messageDto;
    }

    private List<MessageDto> translate(List<Message> messageList) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messageList) {
            messageDtos.add(translate(message));
        }
        return messageDtos;
    }

    private List<ConversationDto> translate1(List<Conversation> conversations) {
        List<ConversationDto> conversationDtos = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationDtos.add(translate(conversation));
        }
        return conversationDtos;
    }

    private ConversationDto translate(Conversation conversation) {
        if (conversation == null) {
            return null;
        }
        ConversationDto conversationDto = new ConversationDto();
        BeanUtils.copyProperties(conversation, conversationDto);
        conversationDto.setInitDate(format.format(conversation.getInitDate()));
        conversationDto.setLastDate(format.format(conversation.getLastDate()));
        conversationDto.setInitiatorName(userRepository.findById(conversationDto.getInitiatorId()).getName());
        TaskDto task = taskService.get(conversation.getTaskId());
        conversationDto.setTask(task);
        conversationDto.setPublisherName(task.getPublisherName());
        return conversationDto;
    }

}
