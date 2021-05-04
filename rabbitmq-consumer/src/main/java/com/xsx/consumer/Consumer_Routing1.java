package com.xsx.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


//RabbitMQ 消费者接收消息  前5步跟生产者一样
public class Consumer_Routing1 {
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
        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";
        channel.queueDeclare(queue1Name, true, false, false,null);

        //6.接收消息
        Consumer consumer = new DefaultConsumer(channel){
               /*
                handleDelivery(String consumerTag, Envelope envelope,
                                AMQP.BasicProperties properties, byte[] body)
                重写回调方法，当收到消息后，会自动执行该方法
                参数:
                    1. consumerTag：标识
                    2. envelope：获取一些信息，交换机，路由key...
                    3. properties:配置信息
                    4. body：数据
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println("consumerTag："+consumerTag);
//                System.out.println("Exchange："+envelope.getExchange());
                System.out.println("RoutingKey："+envelope.getRoutingKey());
//                System.out.println("properties："+properties);
                System.out.println("body："+new String(body));
                System.out.println("将错误日志存储到数据库~~~~");
            }
        };

          /*
        basicConsume(String queue, boolean autoAck, Consumer callback)
        参数：
            1. queue：队列名称
            2. autoAck：是否自动确认
            3. callback：回调对象
         */
        channel.basicConsume(queue1Name, true, consumer);

        //消费者类不需要关闭资源
    }
}
