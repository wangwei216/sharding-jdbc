/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @FileName: RabbitMQConsumer.java
 * @Description: RabbitMQConsumer.java类说明
 * @Author: wangwei
 * @Date: 2019/7/4 16:12
 */
@Component
@RabbitListener(queues = "hello-wangwei")
public class RabbitMQConsumer {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }

}
