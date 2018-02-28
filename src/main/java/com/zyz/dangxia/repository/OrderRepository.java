package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer>{
    Order findById(int id);
}
