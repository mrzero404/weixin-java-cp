package cn.com.lunaler.wx.cp.controller;

import cn.com.lunaler.wx.cp.config.WxCpConfiguration;
import cn.com.lunaler.wx.cp.dao.UserMapper;
import cn.com.lunaler.wx.cp.entity.Staff;
import cn.com.lunaler.wx.cp.entity.User;
import cn.com.lunaler.wx.cp.service.CheckInOutService;
import cn.com.lunaler.wx.cp.service.CountService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpDepartmentService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Controller

public class CountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CheckInOutService checkInOutService;

    @Autowired
    private CountService countService;

    private Map<String,String> userMap = new HashMap<String, String>();

    @PostMapping("/getDailyCount")
    @ResponseBody
    public Object getDailyCount(@RequestBody List<Staff> staffList, @RequestParam(value = "dates")List<String> dates) {
        Map<String,Object> result = new HashMap<String, Object>();
        Integer departmentId = 0;
        Map<Integer, List<Staff>> classfiyStaffMap = new HashMap<>();
        for (Staff staff : staffList) {
            this.logger.debug(String.valueOf(staff.getDepartmentId()));
            if (staff.getDepartmentId() != null){
                this.logger.debug("id isnot null"+String.valueOf(staff.getDepartmentId()));

                if (departmentId.equals(staff.getDepartmentId()) ) {
                    this.logger.debug(String.valueOf(departmentId)+" staff"+String.valueOf(staff.getDepartmentId()));
                    classfiyStaffMap.get(staff.getDepartmentId()).add(staff);
                }else {
                    this.logger.debug("else");
                    classfiyStaffMap.put(staff.getDepartmentId(), new ArrayList<>());
                    classfiyStaffMap.get(staff.getDepartmentId()).add(staff);
                    departmentId = staff.getDepartmentId();
                }
                System.out.println(staff.getLabel());
            }
        }
        result.put("msg",10000);
        result.put("dates",dates);
        result.put("attendanceList",countService.getDailyCount(classfiyStaffMap,dates));

        return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
    }

    @RequestMapping("/getStaffs")
    public Object getTest() throws WxErrorException {
        Map<String,Object> result = new HashMap<String, Object>();
        List<Staff> staffList = new ArrayList<Staff>();
        WxCpService wxCpService = WxCpConfiguration.getCpServices().get(1000002);
        WxCpDepartmentService wxCpDepartmentService = wxCpService.getDepartmentService();

        WxCpDepart wxCpDepart = new WxCpDepart();
        wxCpDepart.setId(700187114);
        Staff staff = new Staff(200001000,"露乐集团",new ArrayList<>());
        staff.getChildren().add(new Staff(700187114,"IT部",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187114)));
        wxCpDepart.setId(700187131);
        staff.getChildren().add(new Staff(700187131,"品牌中心",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187131)));
        wxCpDepart.setId(700187103);
        staff.getChildren().add(new Staff(700187103,"会员营销中心",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187103)));
        wxCpDepart.setId(700187085);
        staff.getChildren().add(new Staff(700187085,"渠道中心",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187085)));
        wxCpDepart.setId(700187092);
        staff.getChildren().add(new Staff(700187092,"采购中心",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187092)));
        wxCpDepart.setId(700187117);
        staff.getChildren().add(new Staff(700187117,"法务部",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187117)));
        wxCpDepart.setId(700187128);
        staff.getChildren().add(new Staff(700187128,"总裁办",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187128)));
        wxCpDepart.setId(700187124);
        staff.getChildren().add(new Staff(700187124,"财务中心",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187124)));
        wxCpDepart.setId(700187106);
        staff.getChildren().add(new Staff(700187106,"人力资源中心",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187106)));
        wxCpDepart.setId(700187112);
        staff.getChildren().add(new Staff(700187112,"研发中心",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187112)));
        wxCpDepart.setId(700187005);
        staff.getChildren().add(new Staff(700187005,"电商中心",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187005)));
        wxCpDepart.setId(700187171);
        staff.getChildren().add(new Staff(700187171,"海外事业部",getDepartmentTreeNode(wxCpDepartmentService, wxCpDepart,700187171)));
        staffList.add(staff);
        result.put("data2",staffList);
        result.put("msg",10000);
        return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
    }

    public List<Staff> getDepartmentTreeNode(WxCpDepartmentService wxCpDepartmentService, WxCpDepart wxCpDepart, Integer departmentId) throws WxErrorException {
        //部门id
        List<WxCpDepart> departList = wxCpDepartmentService.list(wxCpDepart.getId());
        List<Staff> staffList = new ArrayList<>();
        WxCpService wxCpService = WxCpConfiguration.getCpServices().get(3010011);
        List<WxCpUser> wxCpUserList = wxCpService.getUserService().listByDepartment(wxCpDepart.getId(), false, 0);
        for (WxCpUser wxCpUser : wxCpUserList) {
            if (!wxCpUser.getMobile().isEmpty()){
                staffList.add(new Staff(wxCpUser.getMobile().isEmpty() ? 0 : Long.valueOf(wxCpUser.getMobile()),wxCpUser.getName(),new ArrayList<>(),departmentId));
            }
        }
        for (WxCpDepart wxCpDepartNode : departList) {
            Integer wxCpDepartNodeId = wxCpDepartNode.getId();
            if (!wxCpDepartNodeId.equals(wxCpDepart.getId()) && !wxCpDepartNodeId.equals(700187102) && !wxCpDepartNodeId.equals(700187094) && !wxCpDepartNodeId.equals(700187097)){
                if (wxCpDepart.getId().equals(wxCpDepartNode.getParentId())){
                    staffList.add(new Staff(wxCpDepartNode.getId(),wxCpDepartNode.getName(),getDepartmentTreeNode(wxCpDepartmentService, wxCpDepartNode,departmentId)));
                }
            }
        }
        return staffList;
    }

    @RequestMapping("/getStaffJson")
    public Object getStaffJson(){
        Map<String,Object> result = new HashMap<String,Object>();
        return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
    }




}
