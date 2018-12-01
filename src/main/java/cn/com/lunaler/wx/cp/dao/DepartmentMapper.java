package cn.com.lunaler.wx.cp.dao;

import cn.com.lunaler.wx.cp.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
