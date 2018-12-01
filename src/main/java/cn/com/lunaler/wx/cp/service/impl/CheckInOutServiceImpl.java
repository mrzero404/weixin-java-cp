package cn.com.lunaler.wx.cp.service.impl;

import cn.com.lunaler.wx.cp.constant.Status;
import cn.com.lunaler.wx.cp.entity.CheckTime;
import cn.com.lunaler.wx.cp.utils.TimeHandle;
import cn.com.lunaler.wx.cp.dao.CheckInOutMapper;
import cn.com.lunaler.wx.cp.dao.HolidayMapper;
import cn.com.lunaler.wx.cp.entity.CheckInOut;
import cn.com.lunaler.wx.cp.entity.Department;
import cn.com.lunaler.wx.cp.service.CheckInOutService;
import cn.com.lunaler.wx.cp.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/20 17:34
 */
@Service
public class CheckInOutServiceImpl implements CheckInOutService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckInOutMapper checkInOutMapper;

    @Autowired
    private HolidayMapper holidayMapper;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取一个月中有打卡数据的状态
     * @param SSN
     * @param time
     * @return
     */
    public List<CheckTime> getCheckTimeByMonth(String SSN, String time){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //当天日期
        int today = calendar.get(Calendar.DATE);
        Department department = departmentService.getDepartmentBySSN(SSN);
        List<CheckInOut> minCheckInOutList = checkInOutMapper.getMinChecktimeBySSN(SSN,time);
        List<CheckInOut> maxCheckInOutList = checkInOutMapper.getMaxChecktimeBySSN(SSN,time);
        List<CheckTime> checkTimeList = new ArrayList<CheckTime>();
        //获取用户所在部门的迟到起算时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String lateTime = department.getWorkingHour();
        Date date = null;
        try {
            date = sdf.parse(lateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //在工作时间上加5分钟
        lateTime = sdf.format(date.getTime()+300000);
        int index = 0;
        //处理当天日期没数据的情况
        List<CheckInOut> checkInOutList = checkInOutMapper.getChecktimeByDate(SSN, format.format(new Date()).substring(0,10));
        if (TimeHandle.timeDifference(format.format(new Date()).substring(11,19),lateTime)<-30 && checkInOutList.size() == 0){
            checkTimeList.add(new CheckTime(new Date().getTime(),0));
        }
        //处理有数据的情况
        for(CheckInOut checkInOut : minCheckInOutList) {
            //根据计算得出的时间差值判断是否迟到并设置状态 0为正常 1为异常
            int late =
                TimeHandle.judgeStatus(
                    TimeHandle.timeDifference(
                        checkInOut.getDatatime().substring(11,19),lateTime)).getStatus()> Status.Normally.getStatus() ? 1 : 0;

            //根据计算得出的时间差值判断是否早退并设置状态
            //非负数为正常下班把值设为0，负数为早退把值设为1
            int leaveEearly = TimeHandle.timeDifference(department.getOffWorkingHours(),maxCheckInOutList.get(index).getDatatime().substring(11,19)) >= 0 ? 0 : 1;
            index ++;
            try {
                long datatime = format.parse(checkInOut.getDatatime()).getTime();
                //这里0为异常，1为正常（注意与上面区分开来）
                //判断是否当天是否有打卡数据，这里要做特殊处理与过去有完整一天的数据区分开来
                if (Integer.valueOf(checkInOut.getDatatime().substring(8,10)) == today ){
                    //只要早上迟到或旷工这一天就是异常状态
                    //如早上打卡正常到下班打卡中间这段时间为无状态
                    //到下班时间之后打卡判定这一天为正常状态
                    if (late == 1) {
                        checkTimeList.add(new CheckTime(datatime,0));
                    } else if (late == 0 && leaveEearly == 0) {
                        checkTimeList.add(new CheckTime(datatime,1));
                    }
                } else {
                    checkTimeList.add(new CheckTime(datatime,late + leaveEearly > 0 ? 0 : 1));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return checkTimeList;
    }

    /**
     * 获取一个月中的异常状态
     * @param SSN
     * @param yearMomth
     * @return
     */
    public List<CheckTime> getUnusual(String SSN, String yearMomth) {
        /**
         * 利用桶排序的思想
         * 一个月中有多少天就有多少个桶，把上班（有打卡数据），假期（周末、节假日、请假、调休）的日期放入对应的天数
         * 值为0表示既不是假期又没有上班（没有打卡数据），为异常状态（旷工、没带卡、打卡异常）
         * 值为1的桶有两种情况——上班或假期，为正常状态
         * 值为2表示既是假期又来上班，为加班状态（这里后边要考虑申请请假、调休后来上班的情况）
         */
        //获取当月天数
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            calendar.setTime(sdf.parse(yearMomth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //将没有到来的日期标记为正常状态
        int[] total = new int[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
        for (int i=today-1; i<total.length; i++){
            total[i] = total[i] + 1;
        }
        //获取当月假期
        Department department = departmentService.getDepartmentBySSN(SSN);
        List<String> holidayList = null;
        //如果是电商部门另做处理
        if (department.getId() == 700187005) {
            holidayList = holidayMapper.getHolidayByMonth(yearMomth,1);
        } else {
            holidayList = holidayMapper.getHolidayByMonth(yearMomth,0);
        }
        for (String holiday : holidayList) {
            String day = holiday.substring(8,10);
            //当日之后的假期不做处理
            if (Integer.valueOf(day)<=today){
                int index = 0;
                if (day.charAt(0)=='0'){
                    index = Integer.valueOf(holiday.substring(9,10))-1;
                } else {
                    index = Integer.valueOf(holiday.substring(8,10))-1;
                }
                total[index]= total[index]+1;
            }
        }
        //获取有打卡数据的日期
        List<CheckInOut> checkInOutList = checkInOutMapper.getMinChecktimeBySSN(SSN,yearMomth);
        for (CheckInOut checkInOut : checkInOutList) {
            String day = checkInOut.getDatatime().substring(8,10);
            int index = 0;
            if (day.charAt(0)=='0'){
                index = Integer.valueOf(checkInOut.getDatatime().substring(9,10))-1;
            } else {
                index = Integer.valueOf(checkInOut.getDatatime().substring(8,10))-1;
            }
            total[index]= total[index]+1;
        }
        //挑出异常日期添加到数组
        List<CheckTime> checkTimeList = new ArrayList<CheckTime>();
        int index = 0;
        for (int day : total) {
            index ++;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (day == 0) {
                    long datatime = 0;
                    if (index<10) {
                        datatime = format.parse(yearMomth+"-0"+String.valueOf(index)).getTime();
                        this.logger.info("小于零"+yearMomth+"-0"+String.valueOf(index));
                    } else {
                        datatime = format.parse(yearMomth + "-"+String.valueOf(index)).getTime();
                        this.logger.info(yearMomth + "-"+String.valueOf(index));
                    }
                    checkTimeList.add(new CheckTime(datatime,0));
                }
                if (day == 2) {
                    //值为2时为加班状态，有待前端加状态
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return checkTimeList;
    }




}
