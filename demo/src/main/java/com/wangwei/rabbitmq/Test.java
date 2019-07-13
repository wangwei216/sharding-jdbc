/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQBasicProperties;
import com.wangwei.rabbitmq.config.User;
import com.wangwei.rabbitmq.producer.RabbitMQSender;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @FileName: Test.java
 * @Description: Test.java类说明
 * @Author: wangwei
 * @Date: 2019/7/4 16:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private RabbitMQSender helloSender;

    AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder().contentEncoding("utf-8");


    @org.junit.Test
    public void send(){
        helloSender.send();
        User user = new User();
        user.setName("我是实体对象王伟");
        user.setAge(20);

        helloSender.sendMessage(user);
    }

}
