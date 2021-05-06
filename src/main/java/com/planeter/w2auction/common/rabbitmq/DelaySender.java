package com.planeter.w2auction.common.rabbitmq;

import com.planeter.w2auction.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/5/6 19:46
 * @status dev
 */
@Component
@Slf4j
public class DelaySender {
    // AMQP 高级消息队列协议
    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendDelay(OrderEntity order) {
        log.info("【订单生成时间】" + new Date().toString() + "【1分钟后检查订单是否已经支付】" + order.toString());
        this.amqpTemplate.convertAndSend(RabbitMqConfig.ORDER_DELAY_EXCHANGE, RabbitMqConfig.ORDER_DELAY_ROUTING_KEY, order, message -> {
            // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(1 * 1000 * 60 + "");
            return message;
        });
    }
}
