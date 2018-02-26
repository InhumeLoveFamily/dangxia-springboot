package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.ConversationDto;
import com.zyz.dangxia.dto.MessageDto;
import com.zyz.dangxia.entity.Conversation;
import com.zyz.dangxia.entity.Task;
import com.zyz.dangxia.repository.ConversationRepository;
import com.zyz.dangxia.repository.TaskRepository;
import com.zyz.dangxia.repository.UserRepository;
import com.zyz.dangxia.service.ConversationService;
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
    private MessageService messageService;

    @Override
    public List<ConversationDto> getConversation(int userId) {
        //找出自己发布的任务相关的会话+自己主动发起的会话
        List<Integer> myTasks = taskRepository.findTaskIds(userId);
//        logger.info();
        List<Conversation> conversations = conversationRepository.findByTaskIdInOrInitiatorIdIs(myTasks, userId);
        return translate(conversations);
    }

    private List<ConversationDto> translate(List<Conversation> conversations) {
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
        conversationDto.setPublisherName(
                userRepository.findById(
                        taskRepository.findById(
                                conversationDto.getTaskId()).getPublisher()).getName());
        return conversationDto;
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
        }


        messageService.push(conversation.getId(), 1, new Date(), 0,
                task.getRequireVerify() == 1 ?
                        "您好，也许我能提供帮助。" : "您好，由我来为您服务。",0);

        return conversation.getId();
    }

    @Override
    public List<MessageDto> getMsgList(int conId) {
        return null;
    }
}
