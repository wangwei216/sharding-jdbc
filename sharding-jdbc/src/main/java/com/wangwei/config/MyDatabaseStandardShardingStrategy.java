/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.config;


import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;


import java.util.Collection;


/**
 * @FileName: MyDatabaseStandardShardingStrategy.java
 * @Description: MyDatabaseStandardShardingStrategy.java类说明
 * @Author: wangwei
 * @Date: 2019/7/11 14:32
 */
public class MyDatabaseStandardShardingStrategy implements PreciseShardingAlgorithm<Integer> {

    /**
     * 这个是精确的分片算法，【主要是通过userId对数据库进行分库】
     *
     * @param availableTargetNames 这个其实对应的是有效的物理数据源，就是设置分片之后的数据库的名称
     * @param preciseShardingValue 字面上表示精准分片的值，这个可以是一个订单id或者String类型的值，这个就是你设置的分片键是啥，他就是什么类型的值
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> preciseShardingValue) {
        System.out.println("【分库集合为】------》" + availableTargetNames.toString());
        for (String dateBase : availableTargetNames) {
            if (dateBase.contains("_0") && (preciseShardingValue.getValue()) % 2 == 0) {
                System.out.println("入库到库中【0】------>" + dateBase + "【user_id为】" + preciseShardingValue.getValue());
                return dateBase;
            }
            if (dateBase.contains("_1") && (preciseShardingValue.getValue()) % 2 == 1) {
                System.out.println("入库到库中【1】" + dateBase + "【user_id为】" + preciseShardingValue.getValue());
                return dateBase;
            }
        }
        return null;
    }

}
