package com.github.binarywang.demo.wx.cp.constant;

import lombok.Data;

/**
 * @author LinWeiYu
 * @date 2018/11/21 13:46
 */

public enum Status {

    //以迟到起算时间为准
    perfectAttendance(0),//>15
    Normally(1),//0~15
    late1(2),//-1~-10
    late2(3),//-11~-30
    absenteeism(4),//-31+
    leaveEearly(5);

    int status;
    Status(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
