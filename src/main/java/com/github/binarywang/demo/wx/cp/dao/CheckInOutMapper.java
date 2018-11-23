package com.github.binarywang.demo.wx.cp.dao;

import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import com.github.binarywang.demo.wx.cp.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CheckInOutMapper {

    @Select("select * from [dbo].[USERINFO]")
    List<UserInfo> selectAll();


    @Select("SELECT\n" +
        "MIN(dbo.CHECKINOUT.CHECKTIME)as datatime\n" +
        "FROM\n" +
        "dbo.USERINFO ,\n" +
        "dbo.CHECKINOUT\n" +
        "WHERE\n" +
        "dbo.USERINFO.USERID = dbo.CHECKINOUT.USERID AND\n" +
        "dbo.USERINFO.SSN = #{SSN} AND\n" +
        "CONVERT(VARCHAR(25), CHECKTIME, 126) LIKE CONCAT(#{time}, '%')" +
        "GROUP BY\n" +
        "CONVERT(VARCHAR(10), CHECKTIME, 120)")
    List<CheckInOut> getChecktimeBySSN(@Param("SSN") String SSN,@Param("time" )String time);

    @Select("SELECT\n" +
        "dbo.CHECKINOUT.USERID,\n" +
        "min(dbo.CHECKINOUT.CHECKTIME)as checkTime\n" +
        "FROM\n" +
        "dbo.CHECKINOUT\n" +
        "WHERE\n" +
        "CONVERT(VARCHAR(25), CHECKTIME, 126) LIKE CONCAT(#{time}, '%')\n" +
        "GROUP BY\n" +
        "dbo.CHECKINOUT.USERID")
    List<CheckInOut> getMinChecktimeByDate(String time);
}
