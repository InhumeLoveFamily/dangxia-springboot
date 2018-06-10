package com.zyz.dangxia.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FileLoaderTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FileLoader fileLoader;

    @Test
    public void upload() {
    }

    /**
     * 已成功读取到文件
     */
    @Test
    public void load() {
        String path = "D:\\J2EE\\dangxia\\src\\main\\webapp\\upload-dir\\TIM截图20180404214454.png";
        try {
            byte[] result = fileLoader.load(path);
        } catch (IOException e) {
            logger.info("凉了呀");
            e.printStackTrace();
        }
    }
}