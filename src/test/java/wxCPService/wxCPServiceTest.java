package wxCPService;

import cn.com.lunaler.wx.cp.WxCpDemoApplication;
import cn.com.lunaler.wx.cp.config.WxCpConfiguration;
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
}
