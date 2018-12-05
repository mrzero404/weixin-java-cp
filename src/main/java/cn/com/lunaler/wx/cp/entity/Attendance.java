package cn.com.lunaler.wx.cp.entity;

import lombok.Data;

/**
 * @author LinWeiYu
 * @date 2018/11/20 16:40
 */
@Data
public class Attendance {

    /**
     * 编号
     */
    private int id;

    /**
     * 部门
     */
    private String department;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 电话号码
     */
    private String SSN;

    /**
     * 日期
     */
    private String date;

    /**
     * 星期
     */
    private String week;

    /**
     * 上班时间
     */
    private String workingHour;

    /**
     * 下班时间
     */
    private String offWorkingHours;

    /**
     * 是否正常上班
     */
    private String workNormally;

    /**
     * 是否正常下班
     */
    private String normalLeaveWork;

    /**
     * 出勤时长
     */
    private String attendanceTime;

    /**
     * 全勤奖
     */
    private String perfectAttendance;


    /**
     * 是否迟到
     */
    private String isLate;


    /**
     * 迟到分钟数
     */
    private String minutesLate;

    /**
     * 迟到赞助
     */
    private String sponsorship;

    /**
     * 是否早退
     */
    private String isLeaveEearly;

    /**
     * 早退分钟数
     */
    private String leaveEearlyMinutes;

    /**
     * 事假
     */
    private String affairLeave;

    /**
     * 病假
     */
    private String sickLeave;

    /**
     * 调休
     */
    private String paidLeave;

    /**
     * 年假
     */
    private String annualLeave;

    /**
     * 婚假
     */
    private String marriageLeave;

    /**
     * 产假
     */
    private String maternityLeave;

    /**
     * 丧假
     */
    private String funeralLeave;


}
