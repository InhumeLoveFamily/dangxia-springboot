package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer>{
//    List<Message> findBySenderIdIsOrReceiverIdIsOrderByDateAsc(int userId,int userId1);
    List<Message> findByConversationIdOrderByDateAsc(int conId);

    @Query(nativeQuery = true, value = "select * from message where conversation_id = ?1 and message.date > ?2")
    List<Message> get(int conversationId, Date beginDate);
}
