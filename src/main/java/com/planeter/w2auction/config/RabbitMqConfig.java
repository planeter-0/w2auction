package com.planeter.w2auction.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Planeter
 * @description: DLX方式的订单延时队列
 * @date 2021/5/6 17:03
 * @status dev
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 延时队列交换机CustomExchange
     *
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("delay_exchange", "x-delayed-message", true, false, args);
    }


    /**
     * 延时队列
     *
=     */
    @Bean
    public Queue delayQueue() {
        return new Queue("delay_queue", true);
    }

    /**
     * 给延时队列绑定交换机
     *
     */
    @Bean
    public Binding cfgDelayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("delay_key").noargs();
    }
}