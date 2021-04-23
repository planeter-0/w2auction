package com.planeter.w2auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键.
    @Column(unique = true)
    private String username; // 登录账户,唯一.
    private String password; // 密码.
    private String salt; // 加密密码的盐
    private Integer status;//启用状态:0->禁用；1->启用
    @CreatedDate
    private Date createTime;//创建时间

    @OneToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    Member member;

    @JsonIgnoreProperties(value = {"userInfos"})
    @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY) // 延时从数据库中进行加载数据
    @JoinTable(name = "sys_user_role",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<SysRole> roles; // 一个用户具有多个角色

//    @JsonIgnoreProperties(value = {"userInfos"})
//    @ManyToMany(fetch = FetchType.EAGER)// 立即从数据库中进行加载数据(只能有一个eager),用在动态权限上
//    @JoinTable(name = "sys_user_permission",
//            joinColumns = @JoinColumn(name = "uid"),
//            inverseJoinColumns = @JoinColumn(name = "user_permission_id"))
//    private List<SysPermission> permissions; // 细粒度权限, 格式 实体:操作:行id
    /** getter and setter */

    public UserInfo() {

    }
    public UserInfo(String username, String password, String salt, Integer status, List<SysRole> roles) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.status = status;
        this.roles = roles;
    }
}
