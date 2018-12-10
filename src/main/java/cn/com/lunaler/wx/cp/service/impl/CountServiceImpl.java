package cn.com.lunaler.wx.cp.service.impl;

import cn.com.lunaler.wx.cp.dao.CheckInOutMapper;
import cn.com.lunaler.wx.cp.dao.DepartmentMapper;
import cn.com.lunaler.wx.cp.entity.Attendance;
import cn.com.lunaler.wx.cp.entity.CheckInOut;
import cn.com.lunaler.wx.cp.entity.Department;
import cn.com.lunaler.wx.cp.entity.Staff;
import cn.com.lunaler.wx.cp.service.CountService;
import cn.com.lunaler.wx.cp.service.DepartmentService;
import cn.com.lunaler.wx.cp.utils.DateUtil;
import cn.com.lunaler.wx.cp.utils.TimeHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LinWeiYu
 * @date 2018/12/3 16:06
 */
@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private CheckInOutMapper checkInOutMapper;

    @Override
    public List<Attendance> getDailyCount(Map<Integer, List<Staff>> classfiyStaffMap, List<String> dates) {
        List<String> dateList = DateUtil.getBetweenDates(dates);
        List<Attendance> attendanceList = new ArrayList<>();
        //遍历Map集合
        for (Map.Entry<Integer, List<Staff>> entry : classfiyStaffMap.entrySet()) {
            //对应部门成员
            List<Staff> staffList = entry.getValue();
            //查询部门信息
            Department department = departmentMapper.getWorkingHourByDepartment(entry.getKey());
            String depaName = department.getDEPTNAME();
            String depaWorkingHour = department.getWorkingHour();
            String depaOffWorkingHour = department.getOffWorkingHours();

            //遍历对应部门成员
            for (Staff staff : staffList) {



                String SSN = String.valueOf(staff.getId());
                List<CheckInOut> minCheckInOutList = checkInOutMapper.getMinChecktimeInDate(SSN,dateList);
                List<CheckInOut> maxCheckInOutList = checkInOutMapper.getMaxChecktimeInDate(SSN,dateList);
                //下班时间数组下标
                int index = 0;
                for (CheckInOut checkInOut : minCheckInOutList){
                    Attendance attendance = new Attendance();
                    //编号
                    attendance.setId((int)staff.getId());
                    //部门
                    attendance.setDepartment(depaName);
                    //姓名
                    attendance.setName(staff.getLabel());
                    //日期
                    attendance.setDate(checkInOut.getDatatime().substring(0,10));
                    //星期
                    //上班打卡时间
                    String workingHour = checkInOut.getDatatime().substring(11,19);
                    //是否迟到
                    String isLate = TimeHandle.judgeStatus(TimeHandle.timeDifference(workingHour,depaWorkingHour)).toString();
                    //迟到分钟数
                    Integer minutesLate = TimeHandle.timeDifference(workingHour,depaWorkingHour) < 0 ? (int) Math.abs(TimeHandle.timeDifference(workingHour,depaWorkingHour)): null;
                    //下班打卡时间
                    String offWorkingHour = maxCheckInOutList.get(index).getDatatime().substring(11,19);
                    //是否早退
                    String isLeaveEearly = TimeHandle.judgeStatus(TimeHandle.timeDifference(depaOffWorkingHour,offWorkingHour)).toString();
                    //早退分钟数
                    //出勤时长
                    //全勤奖
                    //迟到赞助

                    attendance.setWorkingHour(workingHour);
                    attendance.setOffWorkingHours(offWorkingHour);
                    attendance.setIsLate(isLate);
                    attendance.setIsLeaveEearly(isLeaveEearly);
                    attendance.setMinutesLate(minutesLate);
                    attendanceList.add(attendance);
                }

            }
        }

        return attendanceList;
    }

    @Override
    public List<Attendance> getHeadQuarters() {

        return null;
    }
}
