package cn.com.lunaler.wx.cp.service;

import cn.com.lunaler.wx.cp.entity.Department;

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
    Department getDepartmentBySSN(String SSN);


}
