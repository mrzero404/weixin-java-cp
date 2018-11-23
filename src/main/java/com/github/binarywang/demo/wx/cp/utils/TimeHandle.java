package com.github.binarywang.demo.wx.cp.utils;

import com.github.binarywang.demo.wx.cp.constant.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LinWeiYu
 * @date 2018/11/21 9:59
 */
public class TimeHandle {

    public static long  timeDifference(String checkTime, String workingHour) {
//        System.out.println("签到时间" + checkTime);
//        System.out.println("上班时间" + workingHour);
        DateFormat df = checkTime.contains("-") ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"): new SimpleDateFormat("HH:mm:ss");
        try {
            Date date1 = df.parse(workingHour);
            Date date2 = df.parse(checkTime);
            long diff = date1.getTime() - date2.getTime();
//            System.out.println("毫秒数：" + diff);
            //计算两个时间之间差了多少分钟
            long minutes = diff / (1000 * 60);
            return minutes;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

//    public static long  dateTimeDifference(String checkTime, String workingHour) {
////        System.out.println("签到时间" + checkTime);
////        System.out.println("上班时间" + workingHour);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Date date1 = df.parse(workingHour);
//            Date date2 = df.parse(checkTime);
//            long diff = date1.getTime() - date2.getTime();
////            System.out.println("毫秒数：" + diff);
//            //计算两个时间之间差了多少分钟
//            long minutes = diff / (1000 * 60);
//            return minutes;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return 0;
//    }

    public static Status judgeStatus(Long timeDifference) {
        Status status = Status.Normally;
        if(timeDifference >=15 ){
            return Status.perfectAttendance;
        }else if(timeDifference>=0 && timeDifference<15 ){
            return Status.Normally;
        }else if(timeDifference>=-10 && timeDifference<0 ){
            return Status.late1;
        }else if(timeDifference>=-30 && timeDifference<-10 ){
            return Status.late2;
        }else if(timeDifference<-30 ){
            return Status.absenteeism;
        }
        return status;
    }
}
