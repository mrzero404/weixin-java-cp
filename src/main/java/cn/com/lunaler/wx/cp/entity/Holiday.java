package cn.com.lunaler.wx.cp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LinWeiYu
 * @date 2018/11/20 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {

    /**
     * 主键
     */
    private int id;

    /**
     * 节假日
     */
    private String holiday;

    /**
     * 是否需要工作
     */
    private int isWork;

    /**
     * 是否是节假日
     */
    private int isHoliday;

    /**
     * 是否是大小周
     */
    private int isWeek;

}
