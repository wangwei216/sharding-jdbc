/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.master_slave_config;

import com.google.common.collect.Lists;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import org.apache.shardingsphere.core.strategy.masterslave.RandomMasterSlaveLoadBalanceAlgorithm;
import org.apache.shardingsphere.core.strategy.masterslave.RoundRobinMasterSlaveLoadBalanceAlgorithm;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @FileName: MasterSlaveConfig.java
 * @Description: 这个主要是配置Teacher表的读写分离加上分库分表的内容
 * @Author: wangwei
 * @Date: 2019/7/18 11:01
 */
@Configuration
public class MasterSlaveAndReadWriteConfig {

    /**
     * @Author: wangwei
     * @Description: 核心的三个参数大体配置都在这里
     * @Param: * @Param: null
     * @Date: 2019-07-18
     */
    @Bean
    DataSource getMasterSlaveDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        //配置分库分表的内容
        Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfiguration.getTableRuleConfigs();
        //这里是把主从复制的规则都放到这里了
        shardingRuleConfiguration.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());
        //为了把具体的分库分表的配置策略给注入
        tableRuleConfigs.add(getTeacherTableRuleConfiguration());
        shardingRuleConfiguration.setTableRuleConfigs(tableRuleConfigs);
        //这是配置默认的数据库
        shardingRuleConfiguration.setDefaultDataSourceName("sharding-jdbc");
        //这个是配置一些SQL打印等附加配置
        Properties properties = new Properties();
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), "true");
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfiguration, properties);
    }

    /**
     * @Author: wangwei
     * @Description: 这里主要是进行配置
     * @Param:  * @Param: null
     * @Date: 2019-07-22
     */
    @Bean
    TableRuleConfiguration getTeacherTableRuleConfiguration(){
        TableRuleConfiguration tableRuleConfiguration =
                new TableRuleConfiguration("teacher","db_$->{0..1}.teacher_$->{0..1}");
        //这里是配置分库的配置策略
        ShardingStrategyConfiguration databaseShardingStrategyConfiguration =
                new InlineShardingStrategyConfiguration("age","db_$->{age % 2}");
//        tableRuleConfiguration.setDatabaseShardingStrategyConfig(databaseShardingStrategyConfiguration);
        //这里配置分表的一些策略，是取模不仅可以以Grooy表达式来进行配置分表的表达式，还可以配置分库的表达式
        InlineShardingStrategyConfiguration tableShardingStrategyConfiguration =
                new InlineShardingStrategyConfiguration("teacher_id","teacher_$->{teacher_id % 2}");

        //进行自定义的分库策略
        MyMasterSlaveReadWriteShardingStrategy masterSlaveReadWriteShardingStrategy = new MyMasterSlaveReadWriteShardingStrategy();
        StandardShardingStrategyConfiguration standardShardingStrategyConfiguration =
                new StandardShardingStrategyConfiguration("teacher_id",masterSlaveReadWriteShardingStrategy);
//        tableRuleConfiguration.setTableShardingStrategyConfig(tableShardingStrategyConfiguration);
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(standardShardingStrategyConfiguration);

        return tableRuleConfiguration;
    }

    /**
     * @Author: wangwei
     * @Description: 这个是配置主从复制的配置
     * @Param:  * @Param: null
     * @Param:  * @Param: null
     * @Date: 2019-07-22
     */
    @Bean
    List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
        //正常sharding-jdbc有两种从库的负载均衡算法策略，一种是随机算法，一种是轮循算法
        RandomMasterSlaveLoadBalanceAlgorithm randomLoadBalanceAlgorithm = new RandomMasterSlaveLoadBalanceAlgorithm();
        RoundRobinMasterSlaveLoadBalanceAlgorithm roundRobinMasterSlaveLoadBalance = new RoundRobinMasterSlaveLoadBalanceAlgorithm();
        Properties properties = new Properties();
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(),"true");
        properties.setProperty(ShardingPropertiesConstant.SQL_SIMPLE.getKey(),"true");
        LoadBalanceStrategyConfiguration loadBalanceConfiguration = new LoadBalanceStrategyConfiguration(roundRobinMasterSlaveLoadBalance.getType(),properties);
        System.out.println("打印从库的负载均衡算法---------->"+roundRobinMasterSlaveLoadBalance.getType());
        //这里是配置读写分离的配置
        MasterSlaveRuleConfiguration masterSlaveRuleConfig1 =
                new MasterSlaveRuleConfiguration("db_0", "db0_master0",
                Arrays.asList("db0_slave1", "db0_slave0"),loadBalanceConfiguration);
        MasterSlaveRuleConfiguration masterSlaveRuleConfig2 =
                new MasterSlaveRuleConfiguration("db_1", "db1_master1",
                        Arrays.asList("db1_slave0", "db1_slave1"),loadBalanceConfiguration);
        return Lists.newArrayList(masterSlaveRuleConfig1, masterSlaveRuleConfig2);
    }

    /**
     * @Author: wangwei
     * @Description: 创建真实的数据源
     * @Param: * @Param: null
     * @Date: 2019-07-18
     */
    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(9);
        //主从库0
        result.put("db0_master0", MasterSlaveAndReadWriteConfig.createDataSource("db0_master0"));
        result.put("db0_slave0", MasterSlaveAndReadWriteConfig.createDataSource("db0_slave0"));
        result.put("db0_slave1", MasterSlaveAndReadWriteConfig.createDataSource("db0_slave1"));
        //主从库1
        result.put("db1_master1", MasterSlaveAndReadWriteConfig.createDataSource("db1_master1"));
        result.put("db1_slave0", MasterSlaveAndReadWriteConfig.createDataSource("db1_slave0"));
        result.put("db1_slave1", MasterSlaveAndReadWriteConfig.createDataSource("db1_slave1"));
        //配置默认的数据库
        result.put("sharding-jdbc", MasterSlaveAndReadWriteConfig.createDataSource("sharding-jdbc"));
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
        result.setPassword("root");
        return result;
    }
}
