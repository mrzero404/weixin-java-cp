package com.github.binarywang.demo.wx.cp.service.impl;

import com.github.binarywang.demo.wx.cp.constant.Status;
import com.github.binarywang.demo.wx.cp.dao.CheckInOutMapper;
import com.github.binarywang.demo.wx.cp.dao.HolidayMapper;
import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import com.github.binarywang.demo.wx.cp.entity.CheckTime;
import com.github.binarywang.demo.wx.cp.entity.Department;
import com.github.binarywang.demo.wx.cp.service.CheckInOutService;
import com.github.binarywang.demo.wx.cp.service.DepartmentService;
import com.github.binarywang.demo.wx.cp.utils.TimeHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public List<CheckTime> getCheckTimeByMonth(String SSN, String time){
       Department department = departmentService.getWorkingHour(SSN);
       List<CheckInOut> minCheckInOutList = checkInOutMapper.getMinChecktimeBySSN(SSN,time);
       List<CheckInOut> maxCheckInOutList = checkInOutMapper.getMaxChecktimeBySSN(SSN,time);
       List<CheckTime> checkTimeList = new ArrayList<CheckTime>();
       int index = 0;
       for(CheckInOut checkInOut : minCheckInOutList) {
           //根据计算得出的时间差值判断是否迟到并设置状态 0为正常 1为异常
           int late =
               TimeHandle.judgeStatus(
                   TimeHandle.timeDifference(
                       checkInOut.getDatatime().substring(11,19),department.getWorkingHour())).getStatus()> Status.Normally.getStatus() ? 1 : 0;

           //根据计算得出的时间差值判断是否早退并设置状态
           //非负数为正常下班把值设为0，负数为早退把值设为1
           int leaveEearly = TimeHandle.timeDifference(department.getOffWorkingHours(),maxCheckInOutList.get(index).getDatatime().substring(11,19)) >= 0 ? 0 : 1;
           index ++;
           try {
               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
               long datatime = format.parse(checkInOut.getDatatime()).getTime();
               //0为异常，1为正常
               checkTimeList.add(new CheckTime(datatime,late + leaveEearly > 0 ? 0 : 1));
           } catch (ParseException e) {
               e.printStackTrace();
           }
       }
       return checkTimeList;
    }

    public List<CheckTime> getUnusual(String SSN, String yearMomth) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            calendar.setTime(sdf.parse(yearMomth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int[] total = new int[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
        List<String> holidayList = holidayMapper.getHolidayByMonth(yearMomth);
        for (String holiday : holidayList) {
            String day = holiday.substring(8,10);
            int index = 0;
            if (day.charAt(0)=='0'){
                index = Integer.valueOf(holiday.substring(9,10))-1;
            } else {
                index = Integer.valueOf(holiday.substring(8,10))-1;
            }
            total[index]= total[index]+1;
        }
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
