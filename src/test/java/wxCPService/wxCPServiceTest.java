package wxCPService;

import com.github.binarywang.demo.wx.cp.WxCpDemoApplication;
import com.github.binarywang.demo.wx.cp.config.WxCpConfiguration;
import com.github.binarywang.demo.wx.cp.constant.Status;
import com.github.binarywang.demo.wx.cp.service.CheckInOutService;
import com.github.binarywang.demo.wx.cp.utils.TimeHandle;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpDepartmentService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpDepartmentServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.junit.Assert;
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



    @Test
    public void testJudgeStatus(){
        try {
            WxCpService wxCpService = WxCpConfiguration.getCpServices().get(1000002);
            WxCpUser user = wxCpService.getUserService().getById("13143385664");
            System.out.println(user.getMobile());
            System.out.println(user.getDepartIds()[0]);
            WxCpDepartmentService wxCpDepartmentService = wxCpService.getDepartmentService();
            List<WxCpDepart> departList = wxCpDepartmentService.list(1);
            System.out.println(departList.get(0).getName());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void department() {
        WxCpService wxCpService = WxCpConfiguration.getCpServices().get(3010011);
        try {
            System.out.println(wxCpService.post("https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckinoption","13143385664"));

        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

}
