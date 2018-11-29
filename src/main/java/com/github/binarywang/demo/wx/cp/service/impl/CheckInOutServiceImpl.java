package com.github.binarywang.demo.wx.cp.service.impl;

import com.github.binarywang.demo.wx.cp.constant.Status;
import com.github.binarywang.demo.wx.cp.dao.CheckInOutMapper;
import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import com.github.binarywang.demo.wx.cp.entity.CheckTime;
import com.github.binarywang.demo.wx.cp.service.CheckInOutService;
import com.github.binarywang.demo.wx.cp.utils.TimeHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/20 17:34
 */
@Service
public class CheckInOutServiceImpl implements CheckInOutService {

    @Autowired
    private CheckInOutMapper checkInOutMapper;

//    public List<CheckInOut> getCheckTimeByMonth(String SSN, String time){
//       List<CheckInOut> checkInOutList = checkInOutMapper.getChecktimeBySSN(SSN,time);
//       for(CheckInOut checkInOut : checkInOutList) {
//           //根据计算得出时间差值设置状态
//           checkInOut.setStatus(
//               TimeHandle.judgeStatus(
//                   TimeHandle.timeDifference(
//                       checkInOut.getDatatime().substring(11,19),"08:35:00")).getStatus()> Status.Normally.getStatus() ? 1 : 0);
//           //截取日期舍去时间
////           checkInOut.setDatatime(checkInOut.getDatatime().substring(0,10));
//           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//           try {
//               checkInOut.setDatatime(String.valueOf(format.parse(checkInOut.getDatatime()).getTime()));
////               long datatime = format.parse(checkInOut.getDatatime()).getTime();
////               System.out.println(datatime+":"+status);
////               checkTimeList.add(new CheckTime(datatime,status));
//           } catch (ParseException e) {
//               e.printStackTrace();
//           }
//       }
//       return checkInOutList;
//    }
    public List<CheckTime> getCheckTimeByMonth(String SSN, String time){
       List<CheckInOut> minCheckInOutList = checkInOutMapper.getMinChecktimeBySSN(SSN,time);
       List<CheckInOut> maxCheckInOutList = checkInOutMapper.getMaxChecktimeBySSN(SSN,time);
       List<CheckTime> checkTimeList = new ArrayList<CheckTime>();
       int index = 0;

       for(CheckInOut checkInOut : minCheckInOutList) {
           //根据计算得出的时间差值判断是否迟到并设置状态 0为正常 1为异常
           int late =
               TimeHandle.judgeStatus(
                   TimeHandle.timeDifference(
                       checkInOut.getDatatime().substring(11,19),"08:35:00")).getStatus()> Status.Normally.getStatus() ? 1 : 0;

           //根据计算得出的时间差值判断是否早退并设置状态
           //非负数为正常下班把值设为0，负数为早退把值设为1
           int leaveEearly = TimeHandle.timeDifference("18:00:00",maxCheckInOutList.get(index).getDatatime().substring(11,19)) >= 0 ? 0 : 1;
           index ++;

           //
           try {
               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
               long datatime = format.parse(checkInOut.getDatatime()).getTime();
//               System.out.println(datatime+":"+status);
               //如果
               checkTimeList.add(new CheckTime(datatime,late + leaveEearly > 0 ? 0 : 1));
           } catch (ParseException e) {
               e.printStackTrace();
           }
       }


       return checkTimeList;
    }


}
