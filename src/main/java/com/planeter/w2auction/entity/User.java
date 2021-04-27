package com.planeter.w2auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键.
    @Column(unique = true)
    private String username; // 登录账户,唯一.
    private String nickname;
    private String password; // 密码
    private String salt; // 加密密码的盐
    private Integer status;//启用状态:0->禁用；1->启用
    @CreatedDate
    private Date createTime;//创建时间
    //性别：0->未知；1->男；2->女")
    private Integer gender;
    private Long imageId;
}
