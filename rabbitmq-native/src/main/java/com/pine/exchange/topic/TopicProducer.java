package com.pine.exchange.topic;

import com.pine.exchange.CommonUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 类说明：Topic类型的生产者
 * 假设有交换器 topic_course，
 * 讲课老师有tom,pine,jack，
 * 技术专题有kafka,jvm,redis，
 * 课程章节有 A、B、C，
 * 路由键的规则为 讲课老师+“.”+技术专题+“.”+课程章节，如：king.kafka.A。
 * 生产者--生产全部的消息3*3*3=27条消息
 * @author anzy
 * @version 1.0
 * @date 2020/4/5 19:07
 */
public class TopicProducer {

    public final static String EXCHANGE_NAME = "topic_course";
    /*我们的课程，路由键最终格式类似于：tom.kafka.A  tom.kafka.B*/
    public final static String[] TECHERS={"tom","pine","jack"};

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建一个连接
        Connection connection = CommonUtils.getConnection();

        // 创建一个信道
        Channel channel = connection.createChannel();
        // 指定转发 交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        for(int i=0;i<3;i++){
            String[]  modules={"kafka","jvm","redis"};
            for(int j=0;j<3;j++){
                String[]  servers={"A","B","C"};
                for(int k=0;k<3;k++){
                    // 发送的消息
                    String message = "Hello Topic_["+i+","+j+","+k+"]";
                    String routeKey = TECHERS[i%3]+"."+modules[j%3] + "." + servers[k%3];
                    channel.basicPublish(EXCHANGE_NAME,routeKey, null, message.getBytes());
                    System.out.println(" [x] Sent '" + routeKey +":'"  + message + "'");
                }
            }

        }
        // 关闭频道和连接
        channel.close();
        connection.close();
    }

}
