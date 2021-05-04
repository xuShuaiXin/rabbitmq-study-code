package com.xsx.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String BOOT_TOPIC_EXCHANGE = "boot_topic_exchange";
    public static final String BOOT_QUEUE = "boot_queue";

    //1.声明交换机
    @Bean(value = "bootTopicExchange")
    public Exchange bootTopicExchange(){
        return ExchangeBuilder.topicExchange(BOOT_TOPIC_EXCHANGE).durable(true).build();
    }
    //2.声明队列
    @Bean(value = "bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(BOOT_QUEUE).build();
    }
    //3.绑定交换机和队列
    @Bean
    public Binding bootBinding(@Qualifier("bootTopicExchange") Exchange exchange,
                               @Qualifier("bootQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }
}
