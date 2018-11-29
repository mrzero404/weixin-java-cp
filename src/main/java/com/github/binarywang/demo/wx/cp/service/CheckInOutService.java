package com.github.binarywang.demo.wx.cp.service;

import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import com.github.binarywang.demo.wx.cp.entity.CheckTime;

import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/19 13:47
 */
public interface CheckInOutService {

    /**
     * 获取一个月中有打卡数据的状态
     * @param SSN
     * @param time
     * @return
     */
    List<CheckTime> getCheckTimeByMonth(String SSN, String time);

    /**
     * 获取一个月中的异常状态
     * @param SSN
     * @param yearMomth
     * @return
     */
    List<CheckTime> getUnusual(String SSN, String yearMomth);

}
