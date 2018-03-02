package com.zyz.dangxia.controller;

import com.zyz.dangxia.dto.OrderDto;
import com.zyz.dangxia.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @DeleteMapping("/{orderId}")
    public int cancelOrder(@PathVariable("orderId") int orderId,
                           @RequestParam("userId") int userId) {
        return orderService.cancelAuthor(userId,orderId);
    }

    @GetMapping("/{orderId}")
    public OrderDto get(@PathVariable("orderId") int orderId) {
        return orderService.get(orderId);
    }

    @GetMapping("/{taskId}/byTask")
    public OrderDto getByTaskId(@PathVariable("taskId") int taskId) {
        return orderService.getByTaskId(taskId);
    }

    @PostMapping("{orderId}")
    public int finish(@PathVariable("orderId") int orderId,
                      @RequestParam("date") long date) {
        Date date1 = new Date(date);
        return orderService.finish(orderId,date1);
    }
}
