package com.planeter.w2auction.service;

import com.planeter.w2auction.entity.Message;

import java.util.List;

public interface MessageService {
    void push(Message message);
    List<Message> pull(String username);
}
