package com.zyz.dangxia.controller;

import com.zyz.dangxia.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PostMapping("")
    public int order(@RequestParam("taskId") int taskId,
                     @RequestParam("senderId") int senderId){
        return orderService.add(taskId,senderId,0);
    }
}
