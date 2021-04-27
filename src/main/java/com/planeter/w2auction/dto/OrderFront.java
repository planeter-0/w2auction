package com.planeter.w2auction.dto;

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

    private Long itemId;

    private Long buyerId;

    private boolean complete;
}
