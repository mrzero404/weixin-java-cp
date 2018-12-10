package cn.com.lunaler.wx.cp.constant;

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


    public String toString() {
        String statusStr = "";
        switch (status) {
            case 0 :
                statusStr = "全勤";
                break;
            case 1 :
                statusStr = "正常";
                break;
            case 2 :
                statusStr = "迟到";
                break;
            case 3 :
                statusStr = "迟到";
                break;
            case 4 :
                statusStr = "旷工";
                break;
            case 5 :
                statusStr = "早退";
                break;
            case 6 :
                statusStr = "全勤";
                break;
            case 7 :
                statusStr = "全勤";
                break;


        }
        return statusStr;
    }
}
