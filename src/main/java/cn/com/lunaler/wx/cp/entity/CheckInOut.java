package cn.com.lunaler.wx.cp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author LinWeiYu
 * @date 2018/11/19 14:58
 */
@Data
public class CheckInOut {

//    private int userId;

    private String datatime;

    private int status;

//    public CheckInOut(String checkTime, int status) {
//        this.checkTime = checkTime;
//        this.status = status;
//    }

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
