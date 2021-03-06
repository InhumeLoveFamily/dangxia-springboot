package com.zyz.dangxia.controller;

import com.zyz.dangxia.common.converstion.ConversationDto;
import com.zyz.dangxia.common.converstion.MessageDto;
import com.zyz.dangxia.service.ConversationService;
import com.zyz.dangxia.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    private static final Logger logger = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/{userId}/list")
    public List<ConversationDto> getConversations(@PathVariable("userId") int userId) {
        return conversationService.listByUserId(userId);
    }

    @PostMapping("/{conversationId}/push")
    public int push(@PathVariable("conversationId") int conversationId,
                    @RequestParam("senderId") int senderId,
                    @RequestParam("content") String content,
                    @RequestParam("date") long date,
                    @RequestParam("type") int type) {
        return messageService.push(conversationId, senderId, new Date(date), type, content, 0);
    }

    /*这里用了put，其实符合了rest规范。
     1.put可以用来代表创建和更新。2.这个操作幂等的，无论执行几次，结果都一样。3.url是基于确定的资源上的，而不是基于集合*/
    @PutMapping("/{senderId}/{taskId}")
    public int init(@PathVariable("senderId") int senderId,
                    @PathVariable("taskId") int taskId) {
        return conversationService.initiateConversation(senderId, taskId);
    }

    @GetMapping("/{conversationId}/msglist")
    public List<MessageDto> list(@PathVariable("conversationId") int conId) {
        return conversationService.getMsgList(conId);

    }

    @GetMapping("/{conversationId}/msgListByDate")
    public List<MessageDto> listByDate(@PathVariable("conversationId") int conversationId,
                                       long beginDate) {
        logger.info("beginDate={}", beginDate);
        return conversationService.getMsgList(conversationId, new Date(beginDate));
    }

    @GetMapping("/{conversationId}")
    public ConversationDto get(@PathVariable("conversationId") int conversationId) {
        return conversationService.get(conversationId);
    }
}
