/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.rabbitmq.producer;

import com.wangwei.rabbitmq.config.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @FileName: RabbitMQSender.java
 * @Description: RabbitMQSender.java类说明
 * @Author: wangwei
 * @Date: 2019/7/4 16:47
 */
@Component
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello-我是王伟 " + new Date();
        this.rabbitTemplate.convertAndSend("hello", context);
        System.out.println("发送内容成功-------> " + context);
    }

    public void sendMessage(User user){
        rabbitTemplate.convertAndSend("wangwei",user);
    }

}
