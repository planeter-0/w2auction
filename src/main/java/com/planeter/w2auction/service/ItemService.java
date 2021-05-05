package com.planeter.w2auction.service;

import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.entity.Item;

import java.util.List;

public interface ItemService {
    /** 用户关键字搜索 */
    List<ItemFront> search(String key);
    /** 管理员查看所有item */
    List<ItemFront> viewAll();
    /** 获取商品 */
    Item getItem(Long itemId);
    /** 上传商品 */
    Item uploadItem(ItemFront upload);
    /** 删除未售出商品*/
    void deleteItem(ItemFront front);
    /** 审核,不通过假删除 */
    void verify(Long id,boolean verified);
}
