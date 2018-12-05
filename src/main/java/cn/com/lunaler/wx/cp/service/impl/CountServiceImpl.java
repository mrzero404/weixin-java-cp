package cn.com.lunaler.wx.cp.service.impl;

import cn.com.lunaler.wx.cp.entity.Attendance;
import cn.com.lunaler.wx.cp.entity.Department;
import cn.com.lunaler.wx.cp.service.CountService;
import cn.com.lunaler.wx.cp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/12/3 16:06
 */
public class CountServiceImpl implements CountService {

    @Autowired
    private DepartmentService departmentService;

    @Override
    public List<Attendance> getDailyCount(List<Attendance> attendanceList) {
        for (Attendance attendance : attendanceList) {
            //获取部门上班时间、
            Department department = departmentService.getDepartmentBySSN(attendance.getSSN());
        }
        return null;
    }

    @Override
    public List<Attendance> getHeadQuarters() {

        return null;
    }
}
