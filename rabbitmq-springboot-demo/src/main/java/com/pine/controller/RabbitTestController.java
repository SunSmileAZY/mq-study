package com.pine.controller;


import com.pine.fanout.FanoutSender;
import com.pine.hello.DefaultSender;
import com.pine.topic.TopicSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**

 *类说明：
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitTestController {

    @Autowired
    private DefaultSender defaultSender;
    @Autowired
    private TopicSender topicSender;
    @Autowired
    private FanoutSender fanoutSender;


    /**
     * 普通类型测试
     */
    @GetMapping("/hello")
    public void hello() {
        defaultSender.send("hellomsg!");
    }

    /**
     * topic exchange类型rabbitmq测试
     */
    @GetMapping("/topicTest")
    public void topicTest() {
        topicSender.send();
    }

    /**
     * fanout exchange类型rabbitmq测试
     */
    @GetMapping("/fanoutTest")
    public void fanoutTest() {
        fanoutSender.send("hellomsg:OK");
    }
}
