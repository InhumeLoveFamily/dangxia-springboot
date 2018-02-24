package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer>{
//    List<Message> findBySenderIdIsOrReceiverIdIsOrderByDateAsc(int userId,int userId1);
}
