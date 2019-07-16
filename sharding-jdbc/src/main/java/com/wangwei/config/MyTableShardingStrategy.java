/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.config;

import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @FileName: MyTableShardingStrategy.java
 * @Description: MyTableShardingStrategy.java类说明
 * @Author: wangwei
 * @Date: 2019/7/16 14:56
 */
public class MyTableShardingStrategy implements PreciseShardingAlgorithm<Integer> {


    /**
     * @Author: wangwei
     * @Description: 根据不同的User的age(由于我这个表中没有被的字段，就随便用age)对【表的数量进行取模】把数据分到不同的表中去【分表】
     * @Param:  * @Param: null
     * @Date: 2019-07-16
     */
    @Override
    public String doSharding(Collection<String> tableCollection, PreciseShardingValue<Integer> preciseShardingValue) {
        System.out.println("【分表集合为】------》"+tableCollection.toString());
        for (String table : tableCollection) {
            if (table.contains("_0") && (preciseShardingValue.getValue()) % 3 == 0){
                System.out.println("写入到表【"+table+"】中");
                return table;
            }if (table.contains("_1") && (preciseShardingValue.getValue()) % 3 == 1){
                System.out.println("写入到表【"+table+"】中");
                return table;
            }if (table.contains("_2")&& (preciseShardingValue.getValue()) % 3 == 2){
                System.out.println("写入到表【"+table+"】中");
                return table;
            }
        }
        return null;
    }
}
