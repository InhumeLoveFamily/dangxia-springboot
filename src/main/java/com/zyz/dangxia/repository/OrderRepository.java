package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer>{
    Order findById(int id);
//    @Query("select id from dx_order where status = '1' and executor = '?1'")
//    List<Integer> getServedOrders(int userId);
}
