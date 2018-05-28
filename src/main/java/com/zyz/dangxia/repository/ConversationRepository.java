package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation,Integer>{
    List<Conversation> findByTaskIdInOrInitiatorIdIsOrderByLastDateDesc(List<Integer> ids, int userId);
    Conversation findById(int id);
    Conversation findByTaskIdAndInitiatorId(int taskId,int userId);

    @Query("select id from Conversation where taskId = ?1 and initiatorId = ?2")
    int findIdByTaskIdAndInitiatorId(int taskId, int userId);
}
