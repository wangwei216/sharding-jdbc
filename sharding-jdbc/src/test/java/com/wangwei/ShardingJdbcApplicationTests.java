package com.wangwei;

import com.wangwei.dao.StudentMapper;
import com.wangwei.dao.TeacherMapper;
import com.wangwei.dao.UserMapper;
import com.wangwei.entity.Student;
import com.wangwei.entity.StudentExample;
import com.wangwei.entity.Teacher;
import com.wangwei.entity.User;
import com.wangwei.entity.UserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingJdbcApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(ShardingJdbcApplicationTests.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void testUserList() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
       criteria.andIdBetween(100,240);
        userExample.setOrderByClause("age ASC");
        List<User> userList = userMapper.selectByExample(userExample);
        userList.forEach(item -> System.out.println(item.toString()));
    }

    /**
     * @Author: wangwei
     * @Description: 这个是测试读写分离的老师表
     * @Param:  * @Param: null
     * @Date: 2019-07-22
     */
    @Test
    public void selectTeacherList(){
        Teacher teacher = new Teacher();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacher);
        teacherList.forEach(teacherItem -> System.out.println("遍历打印teacher信息："+teacherItem.toString()));
    }

    /**
     * @Author: wangwei
     * @Description: 这个是用来测试Student表中的范围查询的
     * @Param:  * @Param: null
     * @Date: 2019-07-19
     */
    @Test
    public void selectStudent(){
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        List<Student> studentList = studentMapper.selectByExample(studentExample);
        studentList.forEach(student -> System.out.println(student.toString()));
    }

    /**
     * @Author: wangwei
     * @Description: 测试读写分离的写主库操作
     * @Param:  * @Param: null
     * @Date: 2019-07-22
     */
    @Test
    public void insertTeacher(){
        for (int i = 1;i <= 10;i++){
            Teacher teacher = new Teacher();
            teacher.setAge(i);
            teacher.setName("老师【"+i);
            teacher.setTeacherId(10+i);
            int result = teacherMapper.insert(teacher);
            System.out.println("影响数据库的行数："+result);
        }
        Teacher teacher = new Teacher();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacher);
        teacherList.forEach(teacher1 -> System.out.println("打印插入之后的老师信息-------->"+teacher1.toString()));

    }

    @Test
    public void insert(){
        for (int i = 1; i < 100; i++) {
            User u = new User();
            u.setUserId(100+i); //这个相当userId
            u.setAge(i);
            u.setName("王伟"+i);
            int result = userMapper.insert(u);
            System.out.println("打印影响的行数------>"+result);
            if (result == 1){
                log.info("入库成功：User信息为{}",u.toString());
            }else {
                log.info("入库失败：User信息为{}",u.toString());
                return;
            }
        }
    }

    @Test
    public void testInsertStudent(){
        for (int i =1 ;i<50;i++){
            Student student = new Student();
            student.setName("王伟测试学生表"+i);
            student.setClassId(i);
            student.setStudentId(888);
            int result = studentMapper.insert(student);
            System.out.println("打印插入的记录信息："+result);
        }
    }

}
