package com.wangwei.dao;

import com.wangwei.entity.Teacher;
import com.wangwei.entity.User;
import com.wangwei.entity.UserExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherMapper {
//    int countByExample(UserExample example);

//    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    int insertSelective(User record);

    List<Teacher> selectByExample(@Param("teacher") Teacher teacher);

    User selectByPrimaryKey(Integer id);

//    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

//    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}