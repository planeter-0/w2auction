package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.common.utils.DtoUtils;
import com.planeter.w2auction.dao.ItemDao;
import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.entity.Item;
import com.planeter.w2auction.entity.Message;
import com.planeter.w2auction.service.ItemService;
import com.planeter.w2auction.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    ItemDao itemDao;
    @Resource
    MessageService messageService;


    @Override
    //TODO elasticsearch
    public List<ItemFront> search(String key) {
        //return itemDao.findAllByVerified(true);
        return null;
    }
    //v
    @Override
    public List<ItemFront> viewAll() {
        List<ItemFront> list = new ArrayList<>();
        for(Item i:itemDao.findAll()){
            list.add(DtoUtils.toItemFront(i));
        }
        return list;
    }
    //v
    @Override
    public Item getItem(Long itemId) {
        return itemDao.getOne(itemId);
    }

    //v
    @Override
    public void uploadItem(ItemFront upload) {
        itemDao.save(DtoUtils.toItem(upload));
    }
    //v
    @Override
    public void verify(Long id, boolean verified) {
        Item item = itemDao.getOne(id);
        if (!verified) {
            //TODO redis缓存消息
            String content = item.getName() + "被删除";
            Message m = new Message(content, item.getUsername());
            messageService.push(m);
        }
    }
}
