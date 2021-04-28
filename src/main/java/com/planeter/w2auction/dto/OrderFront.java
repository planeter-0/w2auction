package com.planeter.w2auction.dto;

import com.planeter.w2auction.entity.Item;
import lombok.Data;

import java.util.Date;

/**
 * @author Planeter
 * @description
 * @date 2021/4/27 14:54
 * @status dev
 */
@Data
public class OrderFront {
    private Long id;

    private String address;

    private Date deliverTime;

    private String phone;

    private ItemFront item;

    private Long buyerId;

    private boolean complete;

    public OrderFront(Long id, String address, Date deliverTime, String phone, ItemFront item, Long buyerId, boolean complete) {
        this.id = id;
        this.address = address;
        this.deliverTime = deliverTime;
        this.phone = phone;
        this.item = item;
        this.buyerId = buyerId;
        this.complete = complete;
    }
}
