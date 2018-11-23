package com.github.binarywang.demo.wx.cp.dao;

import com.github.binarywang.demo.wx.cp.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from [dbo].[USERINFO]")
    List<UserInfo> selectAll();


    @Select("SELECT\n" +
        "dbo.CHECKINOUT.CHECKTIME\n" +
        "FROM\n" +
        "dbo.USERINFO ,\n" +
        "dbo.CHECKINOUT\n" +
        "WHERE\n" +
        "dbo.USERINFO.USERID = dbo.CHECKINOUT.USERID AND\n" +
        "dbo.USERINFO.SSN = #{SSN} AND\n" +
        "CONVERT(VARCHAR(25), CHECKTIME, 126) LIKE '%2018-11-%'")
    List<String> getChecktimeBySSN(String SSN);
}
