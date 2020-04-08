package com.pine.consumerbalance.getmessage;

import com.pine.exchange.CommonUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *类说明：普通生产者(淡化)
 */
public class GetMessageProducer {

    public final static String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建一个连接
        Connection connection =  CommonUtils.getConnection();
        // 创建一个信道
        Channel channel = connection.createChannel();
        // 指定转发
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        //所有日志严重性级别
        for(int i=0;i<3;i++){
            //每一次发送一条不同严重性的日志

            // 发送的消息
            String message = "Hello World_"+(i+1);
            //参数1：exchange name
            //参数2：routing key
            channel.basicPublish(EXCHANGE_NAME, "error",
                    null, message.getBytes());
            System.out.println(" [x] Sent 'error':'"
                    + message + "'");
        }
        // 关闭频道和连接
        channel.close();
        connection.close();
    }

}