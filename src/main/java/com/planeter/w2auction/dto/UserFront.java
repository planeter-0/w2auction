package com.planeter.w2auction.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Planeter
 * @description
 * @date 2021/4/27 14:24
 * @status dev
 */
@Data
public class UserFront {
    private Long id; // 主键.
    private String username; // 登录账户,唯一.
    private String nickname;
    private Integer status;//启用状态:0->禁用；1->启用
    private Date createTime;//创建时间
    private Integer gender;
    private Long imageId;
}
