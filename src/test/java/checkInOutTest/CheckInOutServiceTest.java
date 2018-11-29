package checkInOutTest;

import com.github.binarywang.demo.wx.cp.WxCpDemoApplication;
import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import com.github.binarywang.demo.wx.cp.entity.CheckTime;
import com.github.binarywang.demo.wx.cp.service.CheckInOutService;
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
public class CheckInOutServiceTest {

    @Resource
    private CheckInOutService  checkInOutService;

    @Test
    public void getCheckTimeByMonth(){
        List<CheckTime> checkTimeList = checkInOutService.getCheckTimeByMonth("13143385664","2018-11-");

        for(CheckTime checkTime : checkTimeList) {
            System.out.println("日期：" + checkTime.getDatetime());
            System.out.println("状态：" + checkTime.getStatus());

        }
    }
}
