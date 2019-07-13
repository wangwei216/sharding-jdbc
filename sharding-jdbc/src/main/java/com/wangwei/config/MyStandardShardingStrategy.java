/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.config;

import groovy.util.logging.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.util.Collection;


/**
 * @FileName: MyStandardShardingStrategy.java
 * @Description: MyStandardShardingStrategy.java类说明
 * @Author: wangwei
 * @Date: 2019/7/11 14:32
 */
public class MyStandardShardingStrategy implements PreciseShardingAlgorithm<Integer> , RangeShardingAlgorithm {

    /**
     * 这个是精确的分片算法，而且这个是必选的，用于处理=和IN的分片
     *
     * @param availableTargetNames  这个其实对应的是有效的物理数据源，就是设置分片之后的数据库的名称
     * @param preciseShardingValue  字面上表示精准分片的值，这个可以是一个订单id或者String类型的值，这个就是你设置的分片键是啥，他就是什么类型的值
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> preciseShardingValue) {
        System.out.println("打印分片中的信息-------->"+availableTargetNames.toString());
        System.out.println("preciseShardingValue值为------->"+preciseShardingValue.getValue().toString());
        System.out.println("打印进准分片键的值为："+preciseShardingValue.getValue().toString());
        return null;
    }

    //这个是可选的可以自己配置的范围分片算法 用于处理BETWEEN AND分片，如果不配置RangeShardingAlgorithm，SQL中的BETWEEN AND将按照全库路由处理。
    @Override
    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {

        return null;
    }


}
