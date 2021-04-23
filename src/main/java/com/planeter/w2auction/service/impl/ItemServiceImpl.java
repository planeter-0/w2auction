package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.dao.ItemDao;
import com.planeter.w2auction.dto.ItemInfo;
import com.planeter.w2auction.dto.UploadItem;
import com.planeter.w2auction.entity.Item;
import com.planeter.w2auction.service.ItemService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    ItemDao itemDao;

    @Override
    public List<ItemInfo> getAllVerified() {
        return itemDao.findAllByVerified(true);
    }

    @Override
    public Item getDetailedItem(Integer itemId) {
        return itemDao.getOne(itemId.longValue());
    }

    @Override
    public void uploadItem(UploadItem upload) {
        Item item = new Item();
        item.setName(upload.getName());
        item.setPrice(upload.getPrice());
        item.setDetail(upload.getDetail());
        item.setTags(upload.getTags());
        item.setImageId(upload.getImageId());
    }
}
