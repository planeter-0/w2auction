package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDao extends JpaRepository<Item, Long> {
//    List<Item> findAllByVerified(boolean verified);
//    @Query("SELECT new com.planeter.w2auction.dto.ItemInfo(i.id,i.name,i.price,i.userId,u.username,i.imageIds,i.isSold) FROM Item i, User u WHERE i.userId = u.id")
//    List<Item> findAllInfo();
}
