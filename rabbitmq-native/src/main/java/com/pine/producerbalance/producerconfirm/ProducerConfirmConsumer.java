package com.pine.producerbalance.producerconfirm;

import com.pine.exchange.CommonUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *类说明：消费者——发送方确认模式
 */
public class ProducerConfirmConsumer {

    public static void main(String[] argv)
            throws IOException, TimeoutException {
        // 打开连接和创建频道，与发送端一样
        Connection connection = CommonUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(ProducerConfirm.EXCHANGE_NAME,
                BuiltinExchangeType.DIRECT);

        String queueName = ProducerConfirm.EXCHANGE_NAME;
        channel.queueDeclare(queueName,false,false,
                false,null);

        //只关注king
        String routekey="king";
        channel.queueBind(queueName, ProducerConfirm.EXCHANGE_NAME,
                routekey);

        System.out.println(" [*] Waiting for messages......");

        // 创建队列消费者
        final Consumer consumerB = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                //记录日志到文件：
                System.out.println( "Received ["+ envelope.getRoutingKey()
                        + "] "+message);
            }
        };
        channel.basicConsume(queueName, true, consumerB);
    }

}
