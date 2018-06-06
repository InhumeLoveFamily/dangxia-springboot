package com.zyz.dangxia.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void getServedOrders() {
//        System.out.println(orderRepository.getServedOrders(2));
    }
}