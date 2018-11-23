package com.github.binarywang.demo.wx.cp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LinWeiYu
 * @date 2018/11/19 14:58
 */
@Data
public class CheckTime {

//    private int userId;

    private long datetime;

    private int status;

    public CheckTime(long datetime, int status) {
        this.datetime = datetime;
        this.status = status;
    }

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    //    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
//    public String getcheckTime() {
//        return checkTime;
//    }
//
//    public void checkTime(String checkTime) {
//        this.checkTime = checkTime;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
}
