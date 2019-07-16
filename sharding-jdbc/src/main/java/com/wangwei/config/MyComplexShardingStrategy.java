/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.config;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;

/**
 * @FileName: MyComplexShardingStrategy.java
 * @Description: MyComplexShardingStrategy. 这个是实现复杂的策略分片算法
 * @Author: wangwei
 * @Date: 2019/7/15 16:18
 */
public class MyComplexShardingStrategy implements ComplexKeysShardingAlgorithm {
    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {


        return null;
    }
}
