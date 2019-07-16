/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.services;

import com.alibaba.fastjson.JSONArray;
import com.wangwei.entity.User;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: test.java
 * @Description: test.java类说明
 * @Author: wangwei
 * @Date: 2019/7/9 19:53
 */
public class test {
    public static void main(String[] args) {
       String string = new String("wangwei");
        string.intern();
        String str2 = "wangwei";
        System.out.println(string == str2);

        String  str3 = new String("wang") + new String("wei123");
        str3.intern();
        String str4 = "wangwei123";
        System.out.println(str3 == str4);


        String[] strings = {"123","werr"};
        System.out.println(Arrays.toString(strings));

        Set productSet = new HashSet();
        productSet.add("md_capcha");
        productSet.add("md_dataworks");
        String productJson = JSONArray.toJSONString(productSet);
        System.out.println("打印JSON："+productJson);
    }
}
