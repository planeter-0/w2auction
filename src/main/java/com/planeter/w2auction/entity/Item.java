package com.planeter.w2auction.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private String detail;

    private Date created = new Date();

    private String username;

    private String tags;//解析

    private boolean verified;

    private boolean isSold;

    public Item(Long id, String name, Double price, String detail, Date created, String username, String tags, boolean verified, boolean isSold, String imageIds) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.created = created;
        this.username = username;
        this.tags = tags;
        this.verified = verified;
        this.isSold = isSold;
        this.imageIds = imageIds;
    }

    private String imageIds;//解析

    public Item() {

    }
}
