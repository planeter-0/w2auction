package com.planeter.w2auction.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/5/5 10:42
 * @status dev
 */
@Data
@Accessors(chain = true)
@Document(indexName = "item_index", shards = 1, replicas = 0)
public class ESItem implements Serializable {
    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword)
    private Double price;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String detail;

    @Field(type = FieldType.Keyword)
    private Date created = new Date();

    @Field(type = FieldType.Keyword)
    private String username;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String tags;

    @Field(type = FieldType.Boolean)
    private boolean verified;

    @Field(type = FieldType.Boolean)
    private boolean isSold;
}