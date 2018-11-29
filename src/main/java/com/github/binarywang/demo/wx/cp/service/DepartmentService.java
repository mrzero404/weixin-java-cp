package com.github.binarywang.demo.wx.cp.service;

import com.github.binarywang.demo.wx.cp.entity.CheckTime;
import com.github.binarywang.demo.wx.cp.entity.Department;

import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/19 13:47
 */
public interface DepartmentService {

    /**
     * 获取部门上下班时间
     * @param SSN
     * @return
     */
    Department getWorkingHour(String SSN);


}
