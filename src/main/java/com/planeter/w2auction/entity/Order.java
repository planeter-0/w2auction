package com.planeter.w2auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private Date deliverTime;

    private String phone;

    private Long itemId;

    private Long buyerId;

    private boolean complete;
}
