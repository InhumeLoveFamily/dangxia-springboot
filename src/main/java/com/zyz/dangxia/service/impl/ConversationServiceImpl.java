package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.ConversationDto;
import com.zyz.dangxia.entity.Conversation;
import com.zyz.dangxia.repository.ConversationRepository;
import com.zyz.dangxia.repository.TaskRepository;
import com.zyz.dangxia.repository.UserRepository;
import com.zyz.dangxia.service.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Override
    public List<ConversationDto> getconversation(int userId) {
        //找出自己发布的任务相关的会话+自己主动发起的会话
        List<Integer> myTasks = taskRepository.findTaskIds(userId);
//        logger.info();
        List<Conversation> conversations= conversationRepository.findByTaskIdInOrInitiatorIdIs(myTasks,userId);
        return translate(conversations);
    }

    private List<ConversationDto> translate(List<Conversation> conversations) {
        List<ConversationDto> conversationDtos = new ArrayList<>();
        for(Conversation conversation : conversations) {
            conversationDtos.add(translate(conversation));
        }
        return conversationDtos;
    }

    private ConversationDto translate(Conversation conversation) {
        if(conversation == null) {
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
    public int initiateconversation(int initiatorId, int taskId) {
        return 0;
    }
}
