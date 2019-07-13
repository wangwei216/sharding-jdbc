package com.wangwei.dao;

import com.wangwei.entity.Student;
import com.wangwei.entity.StudentExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface StudentMapper {
    int countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}