package com.planeter.w2auction.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    String username;
    String password;
}
