package com.planeter.w2auction.dto;

import com.planeter.w2auction.entity.UserInfo;
import lombok.Data;

import java.util.Date;
@Data
public class FrontMember {
    private Integer id;

    private String username;

    private String phone;

    private String address;

    private Integer gender;

    private Date birthday;

    private Integer imageId;
}
