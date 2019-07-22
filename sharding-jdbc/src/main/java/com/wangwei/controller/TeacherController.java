/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.controller;

import com.wangwei.entity.Teacher;
import com.wangwei.services.servicesImpl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @FileName: TeacherController.java
 * @Description: TeacherController.java类说明
 * @Author: wangwei
 * @Date: 2019/7/22 14:53
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;

    @RequestMapping("/list")
    @ResponseBody
    public List<Teacher> getTeacherList(){
        List<Teacher> teacherList = teacherService.selectTeacherList();
        return teacherList;
    }

}
