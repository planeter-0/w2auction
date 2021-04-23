package com.planeter.w2auction.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;

    @Column(name ="deliver_time")
    private Date deliverTime;

    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id",referencedColumnName = "id")
    private Member seller; //卖家

    @ManyToOne
    @JoinColumn(name = "buyer_id",referencedColumnName="id")
    private Member buyer; //买家

    private boolean complete;
}
