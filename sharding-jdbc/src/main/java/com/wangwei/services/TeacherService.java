/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.services;

import com.wangwei.entity.Teacher;

import java.util.List;

/**
 * @FileName: TeacherService.java
 * @Description: TeacherService.java类说明
 * @Author: admin
 * @Date: 2019/7/22 14:50
 */
public interface TeacherService {

    public List<Teacher> selectTeacherList();

}