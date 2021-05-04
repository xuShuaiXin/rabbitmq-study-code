package com.xsx.producer;

import com.xsx.producer.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqProducerTest{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void test1() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.BOOT_TOPIC_EXCHANGE, "boot.cc", "Hello Springboot and RabbitMQ");
    }

}
