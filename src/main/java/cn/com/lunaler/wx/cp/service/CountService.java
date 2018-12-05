package cn.com.lunaler.wx.cp.service;

import cn.com.lunaler.wx.cp.entity.Attendance;
import cn.com.lunaler.wx.cp.entity.Department;

import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/19 13:47
 */
public interface CountService {

    /**
     * 获取每日统计信息
     * @param attendanceList
     * @return
     */
    List<Attendance> getDailyCount(List<Attendance> attendanceList);

    /**
     * 获取总部人员信息
     * @return
     */
    List<Attendance> getHeadQuarters();

}
