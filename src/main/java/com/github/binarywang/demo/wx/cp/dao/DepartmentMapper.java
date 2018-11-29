package com.github.binarywang.demo.wx.cp.dao;

import com.github.binarywang.demo.wx.cp.entity.Department;
import com.github.binarywang.demo.wx.cp.entity.Holiday;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {

    @Select("SELECT\n" +
        "dbo.department.workingHour,\n" +
        "dbo.department.offWorkingHours\n" +
        "FROM\n" +
        "dbo.department\n" +
        "WHERE\n" +
        "dbo.department.id = #{id}")
    Department getWorkingHourByDepartment(int id);
}
