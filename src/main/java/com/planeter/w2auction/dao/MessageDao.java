package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDao extends JpaRepository<Message, Long> {
    List<Message> getMessagesByTargetEqualsOrderByProducedAtAsc(String target);
}
