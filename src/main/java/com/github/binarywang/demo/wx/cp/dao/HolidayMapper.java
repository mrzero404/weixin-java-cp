package com.github.binarywang.demo.wx.cp.dao;

import com.github.binarywang.demo.wx.cp.entity.Holiday;
import com.github.binarywang.demo.wx.cp.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface HolidayMapper {

    /**
     * 批量插入周末日期
     * @param array
     */
    @Insert({
        "<script>",
        "INSERT INTO dbo.holiday",
            "(id,dbo.holiday.holiday,is_work, is_holiday, is_week)",
        "VALUES"+
            "<foreach item='item' index='index' collection='list'  separator=','>" +
                "(" +
                    "#{item.id},",
                    "#{item.holiday},",
                    "#{item.isWork},",
                    "#{item.isHoliday},",
                    "#{item.isWeek}"+
                ")" +
            "</foreach>",
        "</script>"
    })
    void insertBatchHoliday( List<Holiday> array);

    /**
     * 插入一个节假日
     * @param holiday
     * @return
     */
    @Insert(
        "INSERT INTO holiday" +
            "(holiday, is_work, is_holiday, is_week)" +
        "VALUES" +
            "(#{holiday}, #{isWork}, #{isHoliday}, #{isWeek})"
    )
    int insertHoliday(Holiday holiday);

    /**
     * 查询一个月中的周末日期
     * @param month
     * @return
     */
    @Select("SELECT\n" +
        "dbo.holiday.holiday\n" +
        "FROM\n" +
        "dbo.holiday\n" +
        "WHERE\n" +
        "dbo.holiday.holiday LIKE CONCAT(#{month}, '-%')\n")
    List<String> getHolidayByMonth(String month);

    /**
     * 查询是否与周末表的数据重合
     * @param date
     * @return
     */
    @Select("SELECT\n" +
        "COUNT(id)\n" +
        "FROM\n" +
        "holiday\n" +
        "WHERE\n" +
        "holiday = #{date}")
    int isExist(String date);

    /**
     * 更新节假日与周末重合的日期
     * @param holiday
     * @return
     */
    @Update({"<script>",
        "UPDATE holiday",
        "<set>",
            "<if test='holiday != null'> holiday = #{holiday},</if>",
            "<if test='isWork != null'> is_work = #{isWork},</if>",
            "<if test='isHoliday != null'> is_holiday = #{isHoliday},</if>",
            "<if test='isWeek != null'> is_week = #{isWeek},</if>",
        "</set>",
        "WHERE holiday = #{holiday}",
    "</script>"})
    int updateHoliday(Holiday holiday);



    /**
     * 查询节日缓存表
     * @return List<Holiday>
     */
    @Select("SELECT\n" +
        "dbo.holiday_cache.holiday as holiday,\n" +
        "dbo.holiday_cache.is_work as isWork,\n" +
        "dbo.holiday_cache.is_holiday as isHoliday,\n" +
        "dbo.holiday_cache.is_week as isWeek\n" +
        "FROM\n" +
        "dbo.holiday_cache")
    List<Holiday> getAllHolidayCache();

}
