package com.planeter.w2auction.dto;

import com.planeter.w2auction.entity.Item;
import lombok.Data;

import java.util.List;

@Data
public class UploadItem {

    private String name;

    private Double price;

    private String detail;
    private List<Item> tags;
    private Integer imageId;
}
