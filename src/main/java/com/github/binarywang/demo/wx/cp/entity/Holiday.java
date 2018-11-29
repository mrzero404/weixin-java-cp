package com.github.binarywang.demo.wx.cp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LinWeiYu
 * @date 2018/11/20 16:40
 */
@Data
@AllArgsConstructor
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

}
