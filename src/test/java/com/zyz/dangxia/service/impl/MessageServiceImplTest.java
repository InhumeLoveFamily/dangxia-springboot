package com.zyz.dangxia.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Scanner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageServiceImplTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MessageServiceImpl messageService;

    @Test
    public void push() {
//        Scanner in = new Scanner(System.in);
//
//        while (true) {
//            String msg = in.nextLine();
        messageService.push(3, 5, new Date(), 0, "mqtt3", 0);

//        }
    }
}