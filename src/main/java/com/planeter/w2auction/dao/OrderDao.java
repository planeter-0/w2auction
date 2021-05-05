package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<OrderEntity,Long> {
//    @Query("SELECT new com.planeter.w2auction.dto.OrderDto(o.id,o.address,o.deliverTime,o.phone,o.item,o.buyer.id,o.buyer.userInfo.username,o.complete) FROM Order as o WHERE o.buyer.id = :memberId")
    List<OrderEntity> findOrdersByBuyerId(Long buyerId);
}
