package com.planeter.w2auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planeter.w2auction.entity.SysRole;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class SysPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键.
    private String name; // 权限名称,如 user:select,item:update:12
    @JsonIgnoreProperties(value = {"permissions"})
    @ManyToMany
    @JoinTable(name = "sys_role_permission",
            joinColumns = {@JoinColumn(name = "permission_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<SysRole> roles; // 一个权限可以被多个角色使用

    public SysPermission(String name) {
        this.name = name;
    }

    public SysPermission() {

    }
    /** getter and setter */
}
