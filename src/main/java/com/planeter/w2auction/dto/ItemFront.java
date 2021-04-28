package com.planeter.w2auction.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

/**
 * @description: 搜索结果和管理员页面
 * @author Planeter
 * @date 2021/4/27 11:13
 * @status dev
 */
@Data
public class ItemFront {
    private Long id;
    private String name;
    private Double price;
    private String detail;
    private Date created;
    private String username;
    private List<String> tags;//解析
    private boolean verified;
    private boolean isSold;
    private List<Long> imageIds;//解析

    public ItemFront(Long id, String name, Double price, String detail, Date created, String username, List<String> tags, boolean verified, boolean isSold, List<Long> imageIds) {
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

    public ItemFront(String name, Double price, String detail, Date created, String username, List<String> tags, boolean verified, boolean isSold, List<Long> imageIds) {
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
}