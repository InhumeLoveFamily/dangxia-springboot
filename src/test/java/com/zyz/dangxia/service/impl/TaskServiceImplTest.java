package com.zyz.dangxia.service.impl;

import com.zyz.dangxia.dto.TaskDto;
import com.zyz.dangxia.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskServiceImplTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TaskService taskService;

    @Test
    public void getAll() {
        List<TaskDto> result = taskService.getAll();
        for (TaskDto taskDto : result) {
            logger.info(taskDto.toString());
        }
    }

    @Test
    public void getNearby() {
        List<TaskDto> result = taskService.getNearby(30.271085,120.096896,1.3);
        for (TaskDto taskDto : result) {
            logger.info(taskDto.toString());
        }
    }

    @Test
    public void getAccepted() {
    }

    @Test
    public void getPublished() {
    }

    @Test
    public void add() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }
}