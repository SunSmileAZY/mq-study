package com.pine.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author anzy
 * @version 1.0
 * @date 2020/4/5 19:07
 **/
public class DirectProducer {

    public final static String EXCHANGE_NAME = "direct_pine";

    public static void main(String[] args) throws IOException,TimeoutException {

        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置地址
        connectionFactory.setHost("47.93.206.149");
        // 不写端口号默认5672
        connectionFactory.setPort(20010);
        // 有用户名密码记得设置
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        Connection connection = null;
        Channel channel = null;
        try {
            // 创建连接
            connection = connectionFactory.newConnection();
            // 创建channel信道
            channel = connection.createChannel();
            // 在信道中设置交换器direct/fanout/topic/headers
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // 声明队列（可在消费者设置）
            // 声明路由键
            String[] routeKeys = {"a", "b", "c"};
            int length = routeKeys.length;
            for (int i = 0; i< length; i++){
                String routeKey = routeKeys[i%length];
                String msg = "Hello RabbitMQ direct type " + (i + 1);
                // 发布消息
                channel.basicPublish(EXCHANGE_NAME, routeKey, null, msg.getBytes());
                System.out.println("send: " + routeKey + ":" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            channel.close();
            connection.close();
        }




    }



}
