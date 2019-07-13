/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.rabbitmq.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @FileName: User.java
 * @Description: User.java类说明
 * @Author: wangwei
 * @Date: 2019/7/4 17:07
 */
@Data
public class User implements Serializable {
    private String name;
    private int age;
}
