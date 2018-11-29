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
        "dbo.holiday.holiday\n" +
        "FROM\n" +
        "dbo.holiday\n" +
        "WHERE\n" +
        "dbo.holiday.holiday LIKE CONCAT(#{month}, '-%')\n")
    List<String> getHolidayByMonth(String month);
}
