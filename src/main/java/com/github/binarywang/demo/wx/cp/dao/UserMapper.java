package com.github.binarywang.demo.wx.cp.dao;

import com.github.binarywang.demo.wx.cp.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from [dbo].[USERINFO]")
    List<UserInfo> selectAll();
}
