package cn.com.lunaler.wx.cp.service.impl;

import cn.com.lunaler.wx.cp.dao.CheckInOutMapper;
import cn.com.lunaler.wx.cp.entity.Attendance;
import cn.com.lunaler.wx.cp.entity.CheckInOut;
import cn.com.lunaler.wx.cp.entity.Department;
import cn.com.lunaler.wx.cp.entity.Staff;
import cn.com.lunaler.wx.cp.service.CountService;
import cn.com.lunaler.wx.cp.service.DepartmentService;
import cn.com.lunaler.wx.cp.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/12/3 16:06
 */
public class CountServiceImpl implements CountService {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CheckInOutMapper checkInOutMapper;

    @Override
    public List<Attendance> getDailyCount(List<Staff> staffList, List<String> dates) {
        List<String> dateList = DateUtil.getBetweenDates(dates);
        for (Staff staff : staffList) {
            //获取部门上班时间、
            String SSN = String.valueOf(staff.getId());
            Department department = departmentService.getDepartmentBySSN(SSN);
            List<CheckInOut> minCheckInOutList = checkInOutMapper.getMinChecktimeInDate(SSN,dateList);
            List<CheckInOut> maxCheckInOutList = checkInOutMapper.getMaxChecktimeInDate(SSN,dateList);
        }
        return null;
    }

    @Override
    public List<Attendance> getHeadQuarters() {

        return null;
    }
}
