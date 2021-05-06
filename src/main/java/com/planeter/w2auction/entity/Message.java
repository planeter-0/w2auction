package com.planeter.w2auction.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
/**
 * @description: 消息实体 TODO 整合rabbitmq 实现消息队列
 * @author Planeter
 * @date 2021/4/29 20:19
 * @status dev
 */
@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String target;//用户名
    private String content;
    private Date producedAt;

    public Message(String target, String content) {
        this.target = target;
        this.content = content;
        this.producedAt = new Date();
    }

    public Message() {

    }
}
