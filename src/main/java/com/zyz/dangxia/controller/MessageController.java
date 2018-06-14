package com.zyz.dangxia.controller;

import com.zyz.dangxia.dto.MessageDto;
import com.zyz.dangxia.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private MessageService messageService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

}
