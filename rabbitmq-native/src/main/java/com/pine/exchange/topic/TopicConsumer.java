package com.pine.exchange.topic;

import com.pine.exchange.CommonUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 类说明：topic消费者
 * @author anzy
 * @version 1.0
 * @date 2020/4/5 19:07
 */
public class TopicConsumer {

    public static void main(String[] argv) throws IOException, TimeoutException {
        receive("pine.*.B");
        receive("#.B");
    }

    private static void receive(final String routingKey) throws IOException, TimeoutException {
        // 打开连接和创建频道，与发送端一样
        Connection connection = CommonUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(TopicProducer.EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 声明一个随机队列
        String queueName = channel.queueDeclare().getQueue();
        /**
         * routingKey 参数绑定规则，* 与 #号
         * '*' 匹配一个
         * '#' 匹配多个
         *  例如 #.B 订阅所有B课程， pine.*.B 订阅pine的所有B课程
         */
        channel.queueBind(queueName,TopicProducer.EXCHANGE_NAME, routingKey);

        System.out.println(routingKey + " [*] Waiting for messages:");
        // 创建队列消费者
        final Consumer consumerA = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(routingKey + " AllConsumer Received " + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumerA);
    }
}
