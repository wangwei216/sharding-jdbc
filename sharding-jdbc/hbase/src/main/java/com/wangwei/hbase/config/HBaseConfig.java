/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.hbase.config;

import com.wangwei.hbase.service.HBaseService;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @FileName: HBaseConfig.java
 * @Description: HBaseConfig.java类说明
 * @Author: wangwei
 * @Date: 2019/7/29 17:46
 */
@Configuration
public class HBaseConfig {
    @Value("${HBase.nodes}")
    private String nodes;

    @Value("${HBase.maxsize}")
    private String maxsize;

    @Bean
    public HBaseService getHbaseService(){
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum",nodes );
        conf.set("hbase.client.keyvalue.maxsize",maxsize);

        return new HBaseService();
    }

}
