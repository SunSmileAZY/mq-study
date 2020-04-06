package com.pine.exchange.direct;

import com.pine.exchange.CommonUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author anzy
 * @version 1.0
 * @date 2020/4/5 19:07
 **/
public class DirectProducer {

    public static void main(String[] args) throws IOException,TimeoutException {

        Connection connection = null;
        Channel channel = null;
        try {
            // 创建连接
            connection = CommonUtils.getConnection();
            // 创建channel信道
            channel = connection.createChannel();
            // 在信道中设置交换器direct/fanout/topic/headers
            channel.exchangeDeclare(CommonUtils.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // 声明队列（可在消费者设置）
            int length = CommonUtils.routeKeys.length;
            for (int i = 0; i< 8; i++){
                String routeKey = CommonUtils.routeKeys[i%length];
                String msg = "Hello RabbitMQ direct type " + (i + 1);
                // 发布消息
                channel.basicPublish(CommonUtils.EXCHANGE_NAME, routeKey, null, msg.getBytes());
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
