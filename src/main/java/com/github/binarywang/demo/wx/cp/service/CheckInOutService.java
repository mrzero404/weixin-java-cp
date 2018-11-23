package com.github.binarywang.demo.wx.cp.service;

import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import com.github.binarywang.demo.wx.cp.entity.CheckTime;

import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/19 13:47
 */
public interface CheckInOutService {
    List<CheckTime> getCheckTimeByMonth(String SSN, String time);

}
