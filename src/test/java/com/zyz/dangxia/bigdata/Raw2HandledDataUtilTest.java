package com.zyz.dangxia.bigdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Raw2HandledDataUtilTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void regTest() {
        String s = "aaa，bbb、ccc";
        System.out.println(s.split("[，、]").length);
    }

}