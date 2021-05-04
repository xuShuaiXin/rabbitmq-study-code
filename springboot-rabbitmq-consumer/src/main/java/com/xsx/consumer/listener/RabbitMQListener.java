package com.xsx.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQListener {
    @RabbitListener(queues = {"boot_queue"})
    public void listenQueue(Message message){
        log.info("****message == " + new String(message.getBody()));
    }
}
