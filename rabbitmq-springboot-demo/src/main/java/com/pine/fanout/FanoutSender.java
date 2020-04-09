package com.pine.fanout;

import com.pine.consts.RabbitmqConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**

 *类说明：
 */
@Component
public class FanoutSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String msg) {
        String sendMsg = msg +"---"+ System.currentTimeMillis();;
        System.out.println("FanoutSender : " + sendMsg);
        this.rabbitTemplate.convertAndSend(RabbitmqConst.EXCHANGE_FANOUT, "",sendMsg);
    }

}
