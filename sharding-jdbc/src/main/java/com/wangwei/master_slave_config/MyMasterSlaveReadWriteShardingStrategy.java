/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.master_slave_config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * @FileName: MyMasterSlaveReadWriteShardingStrategy.java
 * @Description: MyMasterSlaveReadWriteShardingStrategy.java类说明
 * @Author: wangwei
 * @Date: 2019/7/24 10:30
 */
public class MyMasterSlaveReadWriteShardingStrategy implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        System.out.println("打印数据库的名称集合------------>"+ Arrays.toString(collection.toArray()));
        System.out.println("打印分片键teacherId的值----->"+preciseShardingValue.toString());
        for (String database: collection){
            if (preciseShardingValue.getValue() % 2 == 0){
                System.out.println("落到库中："+database);
                return database;
            }else {
                System.out.println("落到库中："+database);
                return database;
            }
        }
        return null;
    }
}
