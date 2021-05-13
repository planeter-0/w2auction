package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.common.utils.DtoUtils;
import com.planeter.w2auction.dao.ItemDao;
import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.dto.OrderFront;
import com.planeter.w2auction.entity.Item;
import com.planeter.w2auction.entity.Message;
import com.planeter.w2auction.entity.OrderEntity;
import com.planeter.w2auction.service.ItemService;
import com.planeter.w2auction.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Resource
    ItemDao itemDao;
    @Resource
    MessageService messageService;
    @Resource
    EsItemService esItemService;
    @Resource
    DtoUtils dtoUtils;


    @Override
    @Deprecated //已在EsItemService中实现
    public List<ItemFront> search(String key) {
        //return itemDao.findAllByVerified(true);
        return null;
    }

    @Override
    public List<ItemFront> viewAll() {
        List<ItemFront> list = new ArrayList<>();
        for (Item i : itemDao.findAll()) {
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
    public Item uploadItem(ItemFront upload) {
        return itemDao.save(DtoUtils.toItem(upload));
    }

    @Override
    public void deleteItem(ItemFront front) {
        itemDao.deleteById(front.getId());
    }

    //v
    @Override
    public void verify(Long id, boolean verified) {
        //TODO 写jpql update
        Item item = itemDao.getOne(id);
        if (verified) {
            //mysql修改
            item.setVerified(true);
            itemDao.save(item);
            //es修改
            esItemService.update(DtoUtils.toItemFront(item));
        }// 审核未通过, 只删除mysql, 发送消息. todo 把布尔型verify修改为整形status,0,1,2对应未审核,审核通过,审核未通过
        else {
            itemDao.deleteById(id);
            String content = item.getName() + "被删除";
            //TODO amqp延时队列
            Message m = new Message(item.getUsername(), content);
            messageService.push(m);
            log.info(content + ", 消息已发送给卖家");
        }
    }

    @Override
    public void deleteById(Long id) {
        itemDao.deleteById(id);
    }


    @Override
    public List<ItemFront> getMine(String username, Integer type) {
        List<ItemFront> fronts = new ArrayList<>();
        // 全部
        if (type == 2){
            for (Item i : itemDao.findByUsername(username)) {
                fronts.add(DtoUtils.toItemFront(i));
            }
            return fronts;
        }
        boolean sold = false;
        //未售出
        if (type == 0) {
            sold = false;
        } else if (type == 1) {//已售出
            sold = true;
        }
        for (Item i : itemDao.findByUsernameAndAndVerified(username, sold)) {
            fronts.add(DtoUtils.toItemFront(i));
        }
        return fronts;
    }
}
