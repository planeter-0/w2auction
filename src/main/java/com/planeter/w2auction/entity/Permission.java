package com.planeter.w2auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键.
    private String name; // 权限名称,如 user:select,item:update:12
    @JsonIgnoreProperties("roles")
    @ManyToMany
    List<Role> roles;
}
