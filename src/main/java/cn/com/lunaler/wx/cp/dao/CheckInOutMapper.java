package cn.com.lunaler.wx.cp.dao;

import cn.com.lunaler.wx.cp.entity.CheckInOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CheckInOutMapper {

    /**
     * 获取单个用户一个月中的每天的最小打卡时间
     * @param SSN
     * @param time
     * @return
     */
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
    List<CheckInOut> getMinChecktimeBySSN(@Param("SSN") String SSN,@Param("time" )String time);

    /**
     * 获取单个用户一个月中的每天的最【大】打卡时间
     * @param SSN
     * @param time
     * @return
     */
    @Select("SELECT\n" +
        "MAX(dbo.CHECKINOUT.CHECKTIME)as datatime\n" +
        "FROM\n" +
        "dbo.USERINFO ,\n" +
        "dbo.CHECKINOUT\n" +
        "WHERE\n" +
        "dbo.USERINFO.USERID = dbo.CHECKINOUT.USERID AND\n" +
        "dbo.USERINFO.SSN = #{SSN} AND\n" +
        "CONVERT(VARCHAR(25), CHECKTIME, 126) LIKE CONCAT(#{time}, '%')" +
        "GROUP BY\n" +
        "CONVERT(VARCHAR(10), CHECKTIME, 120)")
    List<CheckInOut> getMaxChecktimeBySSN(@Param("SSN") String SSN,@Param("time" )String time);

    /**
     * 查询单个用户某一天/月/年的打卡时间
     * @param SSN 电话号码
     * @param time 查询日期
     * @return List<CheckInOut>
     */
    @Select("SELECT\n" +
        "dbo.CHECKINOUT.CHECKTIME as datatime\n" +
        "FROM\n" +
        "dbo.USERINFO ,\n" +
        "dbo.CHECKINOUT\n" +
        "WHERE\n" +
        "dbo.USERINFO.USERID = dbo.CHECKINOUT.USERID AND\n" +
        "dbo.USERINFO.SSN = #{SSN} AND\n" +
        "CONVERT(VARCHAR(25), CHECKTIME, 126) LIKE CONCAT(#{time}, '%')")
    List<CheckInOut> getChecktimeByDate(@Param("SSN") String SSN,@Param("time" )String time);

    /**
     * 获取一个月中的
     * @param time
     * @return
     */
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
