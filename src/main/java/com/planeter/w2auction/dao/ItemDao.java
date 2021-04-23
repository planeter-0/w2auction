package com.planeter.w2auction.dao;

import com.planeter.w2auction.dto.ItemInfo;
import com.planeter.w2auction.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemDao extends JpaRepository<Item, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Item  i SET i.name = :name WHERE i.id = :id")
    void updateNameById(String name, Integer id);

    @Query("SELECT new com.planeter.w2auction.dto.ItemInfo(i.name,i.price,i.member.userInfo.username,i.imageId) FROM Item i WHERE i.verified = false")
    List<ItemInfo> findAllByVerified(boolean verified);
}
