package com.planeter.w2auction.service;

import com.planeter.w2auction.dto.ItemInfo;
import com.planeter.w2auction.dto.UploadItem;
import com.planeter.w2auction.entity.Item;

import java.util.List;

public interface ItemService {
    List<ItemInfo> getAllVerified();
    Item getDetailedItem(Integer itemId);
    void uploadItem(UploadItem upload);
}
