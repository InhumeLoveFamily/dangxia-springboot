package com.zyz.dangxia.bigdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TimeSectionUtilTest {

    @Test
    public void getT() {
        int i = TimeSectionUtil.getT(new Date().getTime());
        System.out.println(i + " --------");
    }
}