/**
 * Copyright: 2019 dingxiang-inc.com Inc. All rights reserved.
 */
package com.wangwei.services.servicesImpl;

import com.wangwei.dao.TeacherMapper;
import com.wangwei.entity.Teacher;
import com.wangwei.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName: TeacherServiceImpl.java
 * @Description: TeacherServiceImpl.java类说明
 * @Author: wangwei
 * @Date: 2019/7/22 14:51
 */
@Service
public class TeacherServiceImpl  implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> selectTeacherList() {
        Teacher teacher = new Teacher();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacher);
        return teacherList;
    }
}
