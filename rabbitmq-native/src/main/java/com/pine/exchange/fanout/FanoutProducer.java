package com.pine.exchange.fanout;

import com.pine.exchange.CommonUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 类说明：fanout生产者
 * @author anzy
 * @version 1.0
 * @date 2020/4/5 19:07
 */
public class FanoutProducer {

    public final static String EXCHANGE_NAME = "fanout_pine";

    public final static String[] routeKeys = {"jack","pine","james"};

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建一个连接
        Connection connection = CommonUtils.getConnection();

        // 创建一个信道
        Channel channel = connection.createChannel();
        // 指定转发类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
// 队列，生产者消费者都可以自己进行声明
//        String queueName = "producer_create";
//        channel.queueDeclare(queueName,false,false, false,null);
//        channel.queueBind(queueName,EXCHANGE_NAME,"test");

        /**
         * 日志消息级别，作为路由键使用
         * */
        String[] routekeys = routeKeys;
        for(int i=0;i<3;i++){
            String routekey = routekeys[i%3];//每一次发送一条消息
            // 发送的消息
            String message = "Hello World_"+(i+1);
            //参数1：exchange name
            //参数2：routing key
            channel.basicPublish(EXCHANGE_NAME, routekey, null, message.getBytes());
            System.out.println(" [x] Send '" + routekey +"':'" + message + "'");
        }
        // 关闭频道和连接
        channel.close();
        connection.close();
    }

}
