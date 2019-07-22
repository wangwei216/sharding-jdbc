/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.services.servicesImpl;

import com.wangwei.dao.UserMapper;
import com.wangwei.entity.UserExample;
import com.wangwei.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: UserServiceImpl.java
 * @Description: UserServiceImpl.java类说明
 * @Author: wangwei
 * @Date: 2019/7/22 14:34
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List selectUserList() {
        List userList = new ArrayList();
        UserExample user = new UserExample();
        userList = userMapper.selectByExample(user);
        return userList;
    }
}
