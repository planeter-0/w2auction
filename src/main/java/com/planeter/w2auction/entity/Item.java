package com.planeter.w2auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double price;

    private String detail;
    @CreatedDate
    private Date created;

    @JsonIgnoreProperties(value = {"items"})
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @JsonIgnoreProperties(value = {"items"})
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "item_tag",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Item> tags;

    private boolean verified;

    @Column(name ="is_sold")
    private boolean isSold;

//    @ManyToOne
//    @JoinColumn(name = "tag_id",referencedColumnName = "id")
//    private Tag tagId;

    @Column(name = "image_id")
    private Integer imageId;
}
