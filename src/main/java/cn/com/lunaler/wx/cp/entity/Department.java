package cn.com.lunaler.wx.cp.entity;

import lombok.Data;

/**
 * @author LinWeiYu
 * @date 2018/11/29 19:21
 */
@Data
public class Department {
    /**
     * 部门Id
     */
    private int id;

    /**
     * 部门名称
     */
    private String DEPTNAME;

    /**
     * 上班时间
     */
    private String workingHour;

    /**
     * 下班时间
     */
    private String offWorkingHours;
}
