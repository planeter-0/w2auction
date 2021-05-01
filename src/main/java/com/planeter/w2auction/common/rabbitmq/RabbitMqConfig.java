package com.planeter.w2auction.common.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    /**
     * 商品消息队列
     * @return
     */
    @Bean(name="item")
    public Queue item(){
        return new Queue("user.item");
    }

    /**
     * 账户消息队列
     * @return
     */
    @Bean(name="account")
    public Queue account(){
        return new Queue("user.account");
    }
    @Bean
    TopicExchange exchange(){
        return new TopicExchange("exchange");
    }
    @Bean
    Binding bindingExchangeMessage(@Qualifier("item") Queue item, TopicExchange exchange) {
        return BindingBuilder.bind(item).to(exchange).with("user.#");
    }

    @Bean
    Binding bindingExchangeMessages(@Qualifier("account")Queue account, TopicExchange exchange) {
        return BindingBuilder.bind(account).to(exchange).with("user.#");
    }
}
