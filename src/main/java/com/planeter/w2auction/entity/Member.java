package com.planeter.w2auction.entity;

import com.alibaba.druid.sql.ast.expr.SQLCaseStatement;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="info_id",referencedColumnName="id",nullable=false)
    private UserInfo userInfo;

    private String phone;

    private String address;
    //性别：0->未知；1->男；2->女")
    private Integer gender;

    //生日")
    private Date birthday;

    private Integer imageId;

    @OneToMany(cascade={CascadeType.REMOVE})//级联删除
    List<Item> Items;

    @OneToMany //一个会员作为一个卖家有多个订单
    @JoinColumn(name = "seller_id",referencedColumnName = "id")
    List<Order> sellerOrders;

    @OneToMany //一个会员作为一个买家有多个订单
    @JoinColumn(name = "buyer_id",referencedColumnName="id")
    List<Order> buyerOrders;
}
