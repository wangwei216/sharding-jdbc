/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.config;


import org.apache.commons.dbcp.BasicDataSource;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @FileName: ShardingJdbcConfig.java
 * @Description: ShardingJdbcConfig.java类说明
 * @Author: wangwei
 * @Date: 2019/7/10 9:40
 */
@Configuration
public class ShardingJdbcConfig {

    @Bean
    DataSource getShardingDataSource() throws SQLException {
        //这里是分片规则配置对象
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        //把你需要分片的表给设置一定的分片规则
        Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfig.getTableRuleConfigs();
        tableRuleConfigs.add(getUserTableRuleConfiguration());
        tableRuleConfigs.add(getStudentTableRuleConfiguration());
        //把需要设置分片的逻辑表给绑定表规则列表，其实就是定义一个通用的common表的规则,让别的表也都去复制这个表的通用规则
//        shardingRuleConfig.getBindingTableGroups().add("t_user, t_common");
        //广播表规则列表
//        shardingRuleConfig.getBroadcastTables().add("t_config");
        //这里是相当于给每一个策略再额外添加一个默认的分库或者是分表的策略,这里默认是都写到另一个库中
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "sharding_0.$->{id % 3}"));
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("student_id","sharding_${student_id % 2}"));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
    }


    /**
     * 这个是给学生表设置分库
     *
     * @return
     */
    @Bean
    TableRuleConfiguration getStudentTableRuleConfiguration() {
        //设置逻辑表名称的分片
        // 这里是用配置行表达式分片策略:${['online', 'offline']}_table${1..3} 其实是拼接的online_table1, online_table2, online_table3
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("t_student", "sharding_$->{0..1}.t_user_${1..3}");
        InlineShardingStrategyConfiguration inlineShardingStrategy =
                new InlineShardingStrategyConfiguration("class_id", "sharding_$->{class_id % 2}");

        //这个是给表规则配置设置表的分片策略：
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(inlineShardingStrategy);
        return tableRuleConfiguration;
    }

    /**
     * 给User表设置分库分表的规则
     *
     * @return
     */
    @Bean
    TableRuleConfiguration getUserTableRuleConfiguration() {
        //这个逻辑表意思就是你正常业务中查询用的表名,后面是使用groove的表达式进行散落到真实的不同的表中
        TableRuleConfiguration result = new TableRuleConfiguration("t_user", "sharding_$->{0..1}.t_user_${0..2}");

        //这里是进行把分表分库之后进行分片操作的策略对象都实现了 ShardingStrategyConfiguration 的接口
        // 1.StandardShardingStrategyConfiguration(标准单分片策略)   2.ComplexShardingStrategyConfiguration(复合分片策略)
        // 3.InlineShardingStrategyConfiguration(表达式分片策略)   4.NoneShardingStrategyConfiguration（不分片策略）
        // 5.HintShardingStrategyConfiguration(Hint而非SQL解析的方式分片的策略)
        //这个是对非主键进行分表的，
//        InlineShardingStrategyConfiguration tableStrategyConfiguration =
//                new InlineShardingStrategyConfiguration("user_id","t_user_$->{user_id % 2}");
//        //这个是定义分数据库的策略,一般对主键进行分库
//        InlineShardingStrategyConfiguration datebaseStrategy =
//                new InlineShardingStrategyConfiguration("id","sharding_${id % 2}");
//        result.setDatabaseShardingStrategyConfig(datebaseStrategy);

        //这个是使用定义的精准算法进行分片，对【通过自定义策略实现userId分到不同的数据库中】
        MyDatabaseStandardShardingStrategy myDatabaseStandardShardingStrategy = new MyDatabaseStandardShardingStrategy();
        StandardShardingStrategyConfiguration strategyConfiguration =
                new StandardShardingStrategyConfiguration("user_id", myDatabaseStandardShardingStrategy);
        //这里是可以实现对分表的自定义【通过age分到不同的表中】实现把不同的UserId分到不同的表中
        MyTableShardingStrategy tableShardingStrategy = new MyTableShardingStrategy();
        StandardShardingStrategyConfiguration tableStrategyConfiguration =
                new StandardShardingStrategyConfiguration("age", tableShardingStrategy);

        result.setTableShardingStrategyConfig(tableStrategyConfiguration);
        result.setDatabaseShardingStrategyConfig(strategyConfiguration);

        //设置自增主键,使用的雪花算法，除此之外还有 SNOWFLAKE/ UUID / LEAF_SEGMENT（美团的）
        Properties properties = new Properties();
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), "true");
        KeyGeneratorConfiguration keyGeneratorConfiguration = new KeyGeneratorConfiguration("UUID", "id", properties);
//        result.setKeyGeneratorConfig(keyGeneratorConfiguration);
        return result;
    }

    /**
     * 创建真实数据源的map集合
     *
     * @return
     */
    private static Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(2);
        result.put("sharding_0", createDataSource("sharding_0"));
        result.put("sharding_1", createDataSource("sharding_1"));
        return result;
    }

    /**
     * @param dataSourceName
     * @return
     */
    private static DataSource createDataSource(final String dataSourceName) {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://localhost:3306/%s?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=UTC", dataSourceName));
        result.setUsername("root");
        // sharding-jdbc默认以密码为空的root用户访问，如果修改了root用户的密码，这里修改为真实的密码即可；
        result.setPassword("root");
        return result;
    }


}
