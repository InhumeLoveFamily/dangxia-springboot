package com.zyz.dangxia.controller;

import com.zyz.dangxia.dto.TaskDto;
import com.zyz.dangxia.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{latitude}/{longitude}/{radius}/nearby")
    List<TaskDto> getNearby(@PathVariable("latitude") double latitude,
                            @PathVariable("longitude") double longitude,
                            @PathVariable("radius") double radius) {
        return taskService.getNearby(latitude,longitude,radius);
    }

    @GetMapping("/{userId}/accepted")
    List<TaskDto> getAccpted(@PathVariable("userId") int userId) {
        return taskService.getAccepted(userId);
    }

    @GetMapping("/{userId}/published")
    List<TaskDto> getPublished(@PathVariable("userId") int userId) {
        return taskService.getPublished(userId);
    }

    @PostMapping("/{userId}")
    int add(@PathVariable("userId") int publisher, int type, Date publishDate, Date endDate,
            String content, int requireVerify, String location, double latitude,
            double longitude, double price){
        logger.info(""+publisher+","+publishDate);
        return taskService.add(publisher,type, publishDate, endDate,
                 content, requireVerify, location, latitude,
         longitude, price);
    }

    @DeleteMapping("/{taskId}")
    int delete(@PathVariable("taskId") int taskId) {
        logger.info("delete: "+taskId);
        return taskService.delete(taskId);
    }
}
