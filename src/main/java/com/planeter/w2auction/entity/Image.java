package com.planeter.w2auction.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "image")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    // 0 -> 用户头像, 1 -> 商品图片
    private Integer type;
    @CreatedDate
    private Date upload;

    public Image(String url, Integer type) {
        this.url = url;
        this.type = type;
    }

    public Image() {

    }
}
