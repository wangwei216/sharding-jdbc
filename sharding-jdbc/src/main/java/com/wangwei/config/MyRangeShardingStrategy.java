/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.config;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @FileName: MyRangeShardingStrategy.java
 * @Description: MyRangeShardingStrategy.java类说明
 * @Author: wangwei
 * @Date: 2019/7/19 16:33
 */
public class MyRangeShardingStrategy implements RangeShardingAlgorithm<Integer> , PreciseShardingAlgorithm {
    /**
     * @Author: wangwei
     * @Description: 安照范围进行分片
     * @Param:  * @Param: null
     * @Date: 2019-07-19
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Integer> rangeShardingValue) {
        System.out.println("分片集合的内容："+ Arrays.toString(collection.toArray()));
        System.out.println("RangeShardingValue范围分片值为："+rangeShardingValue.toString());
        Collection<String> collect = new ArrayList<>();
        Range<Integer> valueRange = rangeShardingValue.getValueRange();
        Integer lowerEndpoint = valueRange.lowerEndpoint();
        for (int i =lowerEndpoint; i < valueRange.upperEndpoint();i++){
            for (String item : collection){
               if (i <= 10){
                   System.out.println("小于10以下的------------>"+item);
                    collect.add(item);
                    return collect;
               }else {
                   System.out.println("大于10以上的------------>"+item);
                   collect.add(item);
                   return collect;
               }
            }
        }
        return collect;
    }

    @Override
    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
        return null;
    }
}
