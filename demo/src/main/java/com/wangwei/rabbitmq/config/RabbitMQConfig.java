/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @FileName: RabbitMQConfig.java
 * @Description: RabbitMQConfig.java类说明
 * @Author: wangwei
 * @Date: 2019/7/4 16:44
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue Queue() {
        return new Queue("hello-wangwei");
    }

}
