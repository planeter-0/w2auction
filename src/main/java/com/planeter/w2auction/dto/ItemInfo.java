package com.planeter.w2auction.dto;

import lombok.Data;

@Data
public class ItemInfo {
    String name;
    Double price;
    String username;
    Integer imageId;

    public ItemInfo(String name, Double price, String username, Integer imageId) {
        this.name = name;
        this.price = price;
        this.username = username;
        this.imageId = imageId;
    }
}
