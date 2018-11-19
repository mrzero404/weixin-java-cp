package com.github.binarywang.demo.wx.cp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author LinWeiYu
 * @date 2018/11/19 14:58
 */
public class CheckInOut {

    private long datetime;

    private int status;

    public CheckInOut(long datetime, int status) {
        this.datetime = datetime;
        this.status = status;
    }
//    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
