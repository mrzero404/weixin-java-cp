package com.github.binarywang.demo.wx.cp.service.impl;

import com.github.binarywang.demo.wx.cp.config.WxCpConfiguration;
import com.github.binarywang.demo.wx.cp.dao.DepartmentMapper;
import com.github.binarywang.demo.wx.cp.entity.Department;
import com.github.binarywang.demo.wx.cp.service.DepartmentService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpDepartmentService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/20 17:34
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department getWorkingHour(String SSN) {
        Department department = null;
        try {
            WxCpService wxCpService = WxCpConfiguration.getCpServices().get(1000002);
            WxCpUser user = wxCpService.getUserService().getById(SSN);
            department = getParentId(user.getDepartIds()[0]);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return department;
    }

    public Department getParentId(int parentId) throws WxErrorException {
        WxCpService wxCpService = WxCpConfiguration.getCpServices().get(1000002);
        WxCpDepartmentService wxCpDepartmentService = wxCpService.getDepartmentService();
        List<WxCpDepart> departList = wxCpDepartmentService.list(parentId);
        if (departList.get(0).getParentId()==200001000) {
            return departmentMapper.getWorkingHourByDepartment(departList.get(0).getId());
        } else {
            return getParentId(departList.get(0).getParentId());
        }
    }
}
