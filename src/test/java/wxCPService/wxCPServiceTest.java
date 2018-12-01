package wxCPService;

import cn.com.lunaler.wx.cp.WxCpDemoApplication;
import cn.com.lunaler.wx.cp.config.WxCpConfiguration;
import cn.com.lunaler.wx.cp.dao.CheckInOutMapper;
import cn.com.lunaler.wx.cp.dao.DepartmentMapper;
import cn.com.lunaler.wx.cp.entity.Department;
import cn.com.lunaler.wx.cp.service.CheckInOutService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpDepartmentService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/21 9:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WxCpDemoApplication.class)
public class wxCPServiceTest {

    @Resource
    private CheckInOutService  checkInOutService;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private CheckInOutMapper checkInOutMapper;

    //递归获取部门上下班时间
    @Test
    public void testJudgeStatus(){
        try {
            WxCpService wxCpService = WxCpConfiguration.getCpServices().get(1000002);
            WxCpUser user = wxCpService.getUserService().getById("13143385664");
            System.out.println(user.getMobile());
            System.out.println(user.getDepartIds()[0]);
            Department department = getParentId(user.getDepartIds()[0]);
            System.out.println(department.getWorkingHour());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    public Department getParentId(int parentId) throws WxErrorException {
        WxCpService wxCpService = WxCpConfiguration.getCpServices().get(1000002);
        WxCpDepartmentService wxCpDepartmentService = wxCpService.getDepartmentService();
        List<WxCpDepart> departList = wxCpDepartmentService.list(parentId);
        if (departList.get(0).getParentId()==200001000) {
            return departmentMapper.getWorkingHourByDepartment(departList.get(0).getId());
        } else {
            System.out.println("部门:"+departList.get(0).getName());
            return getParentId(departList.get(0).getParentId());
        }
    }

    //部门信息测试
    @Test
    public void department() {
        WxCpService wxCpService = WxCpConfiguration.getCpServices().get(3010011);
        try {
            System.out.println(wxCpService.post("https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckinoption","13143385664"));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    //消息推送测试
    @Test
    public void message() {
        WxCpService wxCpService = WxCpConfiguration.getCpServices().get(1000004);
        WxCpMessage wxCpMessage = WxCpMessage.TEXT().agentId(1000004).toUser("13143385664").content("test").build();
        try {
            wxCpService.messageSend(wxCpMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    //获取部门下用户700187114
    @Test
    public void listByDepartment() throws WxErrorException {
        WxCpService wxCpServiceMessage = WxCpConfiguration.getCpServices().get(1000004);
        WxCpService wxCpService = WxCpConfiguration.getCpServices().get(3010011);
        String[] departments = {"700187114","700187131","700187103","700187085","700187092","700187117","700187128"
            ,"700187124"
            ,"700187106"
            ,"700187112"};
        List<WxCpUser> users = new ArrayList<WxCpUser>();
        for (String department : departments) {
            users.addAll(wxCpService.getUserService().listByDepartment(Integer.valueOf(department), true, 0));
        }
        List<String> SSNList = checkInOutMapper.getSSNByDay("2018-11-17");
        for (WxCpUser wxCpUser : users) {
            for (String SSN : SSNList) {
                if (wxCpUser.getMobile().contentEquals(SSN)) {
                    wxCpUser.setStatus(0);
                }
            }
        }
        for (WxCpUser wxCpUser : users) {
            if (wxCpUser.getStatus() ==0 ){
                System.out.println(wxCpUser.getName()+" : " +wxCpUser.getMobile()+"  status : "+ wxCpUser.getStatus() + "  userId : " + wxCpUser.getUserId());
//                wxCpServiceMessage.messageSend(WxCpMessage.TEXT().agentId(1000004).toUser(wxCpUser.getUserId()).content("8：30上班打卡").build());
                wxCpServiceMessage.messageSend(WxCpMessage.TEXTCARD().agentId(1000004).toUser(wxCpUser.getUserId()).title("打卡提醒").description("8：30上班打卡").url("URL").build());
            }
        }
    }
}
