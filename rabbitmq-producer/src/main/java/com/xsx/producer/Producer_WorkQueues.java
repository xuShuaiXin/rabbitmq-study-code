package com.xsx.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//RabbitMQ 生产者发送消息 工作队列
public class Producer_WorkQueues {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory factory =  new ConnectionFactory();
        //2.设置参数
        factory.setHost("192.168.43.197");  //ip  默认值 localhost
        factory.setPort(5672);              //端口  默认值 5672
        factory.setVirtualHost("/xsx");     //虚拟机 默认值/
        factory.setUsername("xsx");         //用户名 默认 guest
        factory.setPassword("xsx123456");   //密码 默认值 guest
        //3.从工厂类中获取连接
        Connection connection = factory.newConnection();
        //4.创建channel
        Channel channel = connection.createChannel();
        //5.创建队列 Queue
        /**
         * queueDeclare(String queue, boolean durable,
         *              boolean exclusive, boolean autoDelete,
         *              Map<String, Object>
         *    参数:
         *      1.queue:        队列名称
         *      2.durable:      是否持久化,如果为true会保存到硬盘上,mq重启后也还在
         *      3.exclusive:
         *          * 是否独占。只能有一个消费者监听这队列
         *          * 当Connection关闭时，是否删除队列
         *      4.autoDelete:   是否自动删除
         *      5.arguments:    参数
         */
        //如果没有一个名字叫hello_world的队列，则会创建该队列，如果有则不会创建
        channel.queueDeclare("work_queues", true, false, false,null);

        //6.发送消息
        /**
         * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body
         *    参数:
         *      1. exchange：交换机名称。简单模式下交换机会使用默认的 ""
         *      2. routingKey：路由名称
         *      3. props：配置信息
         *      4. body：发送消息数据
         */
        //发送10条消息
        for (int i = 0; i < 10; i++) {
            String body = "hello work_queues " + i;
            channel.basicPublish("",  "work_queues", null, body.getBytes());
        }

//        //7.关闭资源
        channel.close();
        connection.close();

    }
}
