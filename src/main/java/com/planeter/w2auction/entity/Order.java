package com.planeter.w2auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private Date deliverTime;

    private String phone;

    private Long itemId;

    private Long buyerId;

    private boolean complete;

    public Order(Long id, String address, Date deliverTime, String phone, Long itemId, Long buyerId, boolean complete) {
        this.id = id;
        this.address = address;
        this.deliverTime = deliverTime;
        this.phone = phone;
        this.itemId = itemId;
        this.buyerId = buyerId;
        this.complete = complete;
    }
}
