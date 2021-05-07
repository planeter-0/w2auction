package com.planeter.w2auction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 搜索结果和管理员页面
 * @author Planeter
 * @date 2021/4/27 11:13
 * @status dev
 */
@Data
public class ItemFront implements Serializable {
    private Long id;
    private String name;
    private Double price;
    private String detail;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date created;
    private String username;
    private List<String> tags;//解析
    private boolean verified;
    @JsonProperty(value="isSold")
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

    public ItemFront() {

    }
    //
//    public ItemFront(String name, Double price, String detail, Date created, String username, List<String> tags, boolean verified, boolean isSold, List<Long> imageIds) {
//        this.name = name;
//        this.price = price;
//        this.detail = detail;
//        this.created = created;
//        this.username = username;
//        this.tags = tags;
//        this.verified = verified;
//        this.isSold = isSold;
//        this.imageIds = imageIds;
//    }
}
