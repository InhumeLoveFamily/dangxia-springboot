package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation,Integer>{
    List<Conversation> findByTaskIdInOrInitiatorIdIs(List<Integer> ids, int userId);
}
