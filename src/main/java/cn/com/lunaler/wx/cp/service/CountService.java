package cn.com.lunaler.wx.cp.service;

import cn.com.lunaler.wx.cp.entity.Attendance;
import cn.com.lunaler.wx.cp.entity.Department;
import cn.com.lunaler.wx.cp.entity.Staff;

import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/19 13:47
 */
public interface CountService {

    /**
     * 获取每日统计信息
     * @param staffList
     * @param dates
     * @return
     */
    List<Attendance> getDailyCount(List<Staff> staffList, List<String> dates);

    /**
     * 获取总部人员信息
     * @return
     */
    List<Attendance> getHeadQuarters();

}
