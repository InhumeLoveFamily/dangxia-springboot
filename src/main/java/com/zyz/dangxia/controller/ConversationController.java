package com.zyz.dangxia.controller;

import com.zyz.dangxia.dto.ConversationDto;
import com.zyz.dangxia.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping("/{userId}/list")
    public List<ConversationDto> getconversations(@PathVariable("userId") int userId) {
        return conversationService.getconversation(userId);
    }
}
