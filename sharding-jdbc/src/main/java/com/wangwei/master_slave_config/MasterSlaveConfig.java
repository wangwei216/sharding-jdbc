/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.master_slave_config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import org.apache.shardingsphere.core.strategy.masterslave.RandomMasterSlaveLoadBalanceAlgorithm;
import org.apache.shardingsphere.core.strategy.masterslave.RoundRobinMasterSlaveLoadBalanceAlgorithm;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.spi.masterslave.MasterSlaveLoadBalanceAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @FileName: MasterSlaveConfig.java
 * @Description: MasterSlaveConfig.java类说明
 * @Author: wangwei
 * @Date: 2019/7/18 11:01
 */
@Configuration
public class MasterSlaveConfig {

    /**
     * @Author: wangwei
     * @Description: 核心的三个参数大体配置都在这里
     * @Param: * @Param: null
     * @Date: 2019-07-18
     */
    @Bean
    DataSource getMasterSlaveDataSource() throws SQLException {
        //配置从库的名字
        List<String> slaveDatebaseNameList = Arrays.asList("ds_slave0", "ds_slave1");
        //正常sharding-jdbc有两种从库的负载均衡算法策略，一种是随机算法，一种是轮循算法
        RandomMasterSlaveLoadBalanceAlgorithm randomLoadBalanceAlgorithm = new RandomMasterSlaveLoadBalanceAlgorithm();
        RoundRobinMasterSlaveLoadBalanceAlgorithm roundRobinMasterSlaveLoadBalance = new RoundRobinMasterSlaveLoadBalanceAlgorithm();
        LoadBalanceStrategyConfiguration loadBalanceConfiguration = new LoadBalanceStrategyConfiguration(randomLoadBalanceAlgorithm.getType());
        System.out.println("打印从库的负载均衡算法---------->"+randomLoadBalanceAlgorithm.getType());
        MasterSlaveRuleConfiguration masterSlaveRuleConfig =
                new MasterSlaveRuleConfiguration("ds_master_slave", "ds_master",slaveDatebaseNameList ,loadBalanceConfiguration);
        //这个是配置一些SQL打印等附加配置
        Properties properties = new Properties();
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), "true");
        return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(), masterSlaveRuleConfig, properties);
    }

    /**
     * @Author: wangwei
     * @Description: 创建真实的数据源
     * @Param: * @Param: null
     * @Date: 2019-07-18
     */
    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(3);
        result.put("ds_master", MasterSlaveConfig.createDataSource("ds_master"));
        result.put("ds_slave0", MasterSlaveConfig.createDataSource("ds_slave0"));
        result.put("ds_slave1", MasterSlaveConfig.createDataSource("ds_slave1"));
        return result;
    }

    /**
     * @param dataSourceName
     * @return
     */
    private static DataSource createDataSource(final String dataSourceName) {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://localhost:3306/%s?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=UTC", dataSourceName));
        result.setUsername("root");
        // sharding-jdbc默认以密码为空的root用户访问，如果修改了root用户的密码，这里修改为真实的密码即可；
        result.setPassword("root");
        return result;
    }
}
