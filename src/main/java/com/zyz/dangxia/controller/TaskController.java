package com.zyz.dangxia.controller;

import com.zyz.dangxia.bigdata.TaskClassList;
import com.zyz.dangxia.dto.PriceSection;
import com.zyz.dangxia.dto.TaskClassDto;
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

    @Autowired
    TaskClassList taskClassList;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取附近发布的任务
     * @param latitude
     * @param longitude
     * @param radius
     * @return
     */
    @GetMapping("/{latitude}/{longitude}/{radius}/nearby")
    List<TaskDto> getNearby(@PathVariable("latitude") double latitude,
                            @PathVariable("longitude") double longitude,
                            @PathVariable("radius") double radius) {
//        if(latitude>10)
//            throw new RuntimeException("测试错误");
        return taskService.getNearby(latitude,longitude,radius);
    }

    /**
     * 获取附近发布的快速任务
     * @param latitude
     * @param longitude
     * @param radius
     * @return
     */
    @GetMapping("/{latitude}/{longitude}/{radius}/nearbyQuick")
    List<TaskDto> getNearbyQuick(@PathVariable("latitude") double latitude,
                            @PathVariable("longitude") double longitude,
                            @PathVariable("radius") double radius) {
        return taskService.getNearbyQuick(latitude,longitude,radius);
    }

    /**
     * 获取自己接下的任务
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/accepted")
    List<TaskDto> getAccpted(@PathVariable("userId") int userId) {
        return taskService.getAccepted(userId);
    }

    @GetMapping("/{userId}/published")
    List<TaskDto> getPublished(@PathVariable("userId") int userId) {
        return taskService.getPublished(userId);
    }

    @PostMapping("/{userId}")
    int add(@PathVariable("userId") int publisher, int type, long publishDate, long endDate,
            String content, int requireVerify, String location, double latitude,
            double longitude, double price, int classId) {
        logger.info(""+publisher+","+publishDate);
        return taskService.add(publisher,type, new Date(publishDate), new Date(endDate),
                 content, requireVerify, location, latitude,
                longitude, price, classId);
    }

    @DeleteMapping("/{taskId}")
    int delete(@PathVariable("taskId") int taskId) {
        logger.info("delete: "+taskId);
        return taskService.delete(taskId);
    }

    /**
     * 指定某人接受任务（需审核的任务通过审核之后，授予给某人执行）
     */
    @PostMapping("/{taskId}/{userId}")
    int appoint(@PathVariable("taskId") int taskId,
                @PathVariable("userId") int userId) {
        logger.info("任务"+taskId+"授予给了用户:"+userId);
        return taskService.appoint(taskId,userId);
    }

    @GetMapping("/classes")//查询需求有哪几种类别
    List<TaskClassDto> getClasses() {
        return taskService.getClasses();
    }

    @PostMapping("/evaluation")
//查询估价
    PriceSection getEvaluation(@RequestParam("date") long dateTime,
                               @RequestParam("classId") int classId,
                               @RequestParam("content") String content
    ) {
        return taskService.getPriceSection(classId, content, new Date(dateTime));
    }

    @PostMapping("/newPrice")
    int changePrice(@RequestParam("newPrice") double newPrice,
                    @RequestParam("taskId") int taskId) {
        return taskService.changePrice(newPrice, taskId);
    }

    @GetMapping("/{taskId}/price")
    double getNewPrice(@PathVariable("taskId") int taskId) {
        return taskService.getPrice(taskId);
    }

}
