/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.controller;

import com.wangwei.entity.Teacher;
import com.wangwei.entity.User;
import com.wangwei.services.UserService;
import com.wangwei.services.servicesImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @FileName: UserController.java
 * @Description: UserController.java类说明
 * @Author: wangwei
 * @Date: 2019/7/22 14:40
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/list")
    @ResponseBody
    public List<Teacher> getList(){
        List list = userService.selectUserList();
        return list;
    }

}
