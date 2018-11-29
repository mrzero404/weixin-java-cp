package com.github.binarywang.demo.wx.cp.dao;

import com.github.binarywang.demo.wx.cp.entity.Holiday;
import com.github.binarywang.demo.wx.cp.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface HolidayMapper {

    @Insert({
        "<script>",
        "INSERT INTO dbo.holiday",
            "(id,dbo.holiday.holiday,is_work)",
        "VALUES"+
            "<foreach item='item' index='index' collection='list'  separator=','>" +
                "(" +

                    "#{item.id},",
                    "#{item.holiday},",
                    "#{item.isWork}"+
                ")" +
            "</foreach>",
        "</script>"
    })
    void insertBatchHoliday( List<Holiday> array);

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
