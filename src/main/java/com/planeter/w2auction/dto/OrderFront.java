package com.planeter.w2auction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planeter.w2auction.entity.Item;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Planeter
 * @description
 * @date 2021/4/27 14:54
 * @status dev
 */
@Data
public class OrderFront implements Serializable {
    private Long id;

    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
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

    public OrderFront() {
    }
}
