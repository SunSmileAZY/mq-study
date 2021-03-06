package com.pine.exchange.direct;

import com.pine.exchange.CommonUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费端
 * @author anzy
 * @version 1.0
 * @date 2020/4/5 19:07
 **/
public class NormalConsumer {

    public static void main(String[] argv) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = CommonUtils.getConnection();
        // 创建信道
        Channel channel =connection.createChannel();
        // 在信道中设置交换器
        channel.exchangeDeclare(CommonUtils.EXCHANGE_NAME,BuiltinExchangeType.DIRECT);
        // 声明队列（放在消费者中去做）
        String queueName="queue-a";
        channel.queueDeclare(queueName,false,false,false,null);

        //绑定：将队列(queuq-a)与交换器通过 路由键 绑定(a)
        String routeKey = CommonUtils.routeKeys[0];
        channel.queueBind(queueName,CommonUtils.EXCHANGE_NAME,routeKey);
        System.out.println("waiting for message ......");

        //申明一个消费者
        final Consumer consumer  = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                String message = new String(bytes,"UTF-8");
                System.out.println("Received["+envelope.getRoutingKey()+"]"+message);
            }
        };
        // 消息者正是开始在指定队列上消费。(queue-king)
        // 这里第二个参数是自动确认参数，如果是true则是自动确认
        channel.basicConsume(queueName,true,consumer);
    }

}
