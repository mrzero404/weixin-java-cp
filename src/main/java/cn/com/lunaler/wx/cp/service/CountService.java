package cn.com.lunaler.wx.cp.service;

import cn.com.lunaler.wx.cp.entity.Attendance;
import cn.com.lunaler.wx.cp.entity.Department;
import cn.com.lunaler.wx.cp.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * @author LinWeiYu
 * @date 2018/11/19 13:47
 */
public interface CountService {

    /**
     * 获取每日统计信息
     * @param classfiyStaffMap
     * @param dates
     * @return
     */
    List<Attendance> getDailyCount(Map<Integer, List<Staff>> classfiyStaffMap, List<String> dates);

    /**
     * 获取总部人员信息
     * @return
     */
    List<Attendance> getHeadQuarters();

}
