package com.pine.exchange;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 连接公共方法
 * @author anzy
 * @version 1.0
 * @date 2020/4/5 20:10
 **/
public class CommonUtils {

    public final static String EXCHANGE_NAME = "direct_pine";
    /**
     * 声明路由键
     */
    public final static String[] routeKeys = {"a", "b", "c"};

    public static Connection getConnection() throws IOException, TimeoutException {
        //连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置地址
        connectionFactory.setHost("47.93.206.149");
        // 不写端口号默认5672
        connectionFactory.setPort(20010);
        // 有用户名密码记得设置
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        // 打开连接和创建频道，与发送端一样
        return connectionFactory.newConnection();
    }
}
