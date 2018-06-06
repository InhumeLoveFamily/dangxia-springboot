package com.zyz.dangxia.controller;

import com.zyz.dangxia.dto.PriceSection;
import com.zyz.dangxia.dto.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskControllerTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    TaskController taskController;

    @Test
    public void getTime() {
        logger.info("" + new Date().getTime());
    }

    @Test
    public void getEvaluation() {
        PriceSection priceSection = taskController.getEvaluation(new Date().getTime(), 10,
                "来个健身私教");
        logger.info("price = {}", priceSection.toString());
    }

    @Test
    public void getMyTodoTask() {
        List<TaskDto> result = taskController.getAccpted(21);
        for (TaskDto taskDto : result) {
            logger.info(taskDto.toString());
        }
    }

    @Test
    public void changePrice() {
        logger.info("result = {}", taskController.changePrice(233, 27, 1));
    }
}