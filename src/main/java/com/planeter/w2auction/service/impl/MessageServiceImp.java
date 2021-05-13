package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.dao.MessageDao;
import com.planeter.w2auction.entity.Message;
import com.planeter.w2auction.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: TODO
 * @author Planeter
 * @date 2021/4/27 14:49
 * @status dev
 */
@Service
public class MessageServiceImp implements MessageService {
    @Resource
    private MessageDao messageDao;
    //v
    @Override
    public void push(Message message) {
        //TODO redis缓存
        messageDao.save(message);
    }
    //v
    @Override
    public List<Message> pull(String username) {
        List<Message> messages = messageDao.getMessagesByTargetEqualsOrderByProducedAtAsc(username);
        //todo 消息增加status字段 0->已读, 1->未读
        messageDao.deleteAll(messages);
        return messages;
    }
}
