package com.wangwei;

import com.wangwei.dao.StudentMapper;
import com.wangwei.dao.UserMapper;
import com.wangwei.entity.Student;
import com.wangwei.entity.User;
import com.wangwei.entity.UserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingJdbcApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(ShardingJdbcApplicationTests.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void test() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
       criteria.andIdBetween(80,100);
        List<User> userList = userMapper.selectByExample(userExample);
        userList.forEach(item -> System.out.println(item.toString()));
    }

    @Test
    public void insert(){
        for (int i = 1; i < 10; i++) {
            User u = new User();
            u.setUserId(i); //这个相当userId
            u.setAge(100 + i);
            u.setName("王伟"+i);
            int result = userMapper.insert(u);
            System.out.println("打印影响的行数------>"+result);
            if (result == 1){
                log.info("入库成功：User信息为{}",u.toString());
            }else {
                log.info("入库失败：User信息为{}",u.toString());
            }
        }
    }

    @Test
    public void testInsertStudent(){
        for (int i =1 ;i<5;i++){
            Student student = new Student();
            student.setName("王伟测试学生表"+i);
            student.setClassId(i);
            student.setStudentId(888);
            int result = studentMapper.insert(student);
            System.out.println("打印插入的记录信息："+result);
        }
    }

}
