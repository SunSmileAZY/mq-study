package com.pine.hello;

import com.pine.consts.RabbitmqConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**

 *类说明：
 */
@Component
public class DefaultSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String msg) {
        String sendMsg = msg +"---"+ System.currentTimeMillis();;
        System.out.println("Sender : " + sendMsg);
        this.rabbitTemplate.convertAndSend(RabbitmqConst.QUEUE_HELLO, sendMsg);
        this.rabbitTemplate.convertAndSend(RabbitmqConst.QUEUE_USER, sendMsg);
    }

}
