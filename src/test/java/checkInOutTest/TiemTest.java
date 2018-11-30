package checkInOutTest;

import com.github.binarywang.demo.wx.cp.WxCpDemoApplication;
import com.github.binarywang.demo.wx.cp.constant.Status;
import com.github.binarywang.demo.wx.cp.service.CheckInOutService;
import com.github.binarywang.demo.wx.cp.utils.TimeHandle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LinWeiYu
 * @date 2018/11/21 9:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WxCpDemoApplication.class)
public class TiemTest {

    @Resource
    private CheckInOutService  checkInOutService;

    @Test
    public void testJudgeStatus(){
//        Assert.assertEquals(15,checkInOutService.judgeState("2018-11-20 08:15:00","2018-11-20 08:30:00"));
//        System.out.println(checkInOutService.judgeState("2018-11-20 08:35:00","2018-11-20 08:30:00"));
        Assert.assertEquals(0,TimeHandle.judgeStatus(TimeHandle.timeDifference("2018-11-20 08:20:00","2018-11-20 08:35:00")).getStatus());
        Assert.assertEquals(1,TimeHandle.judgeStatus(TimeHandle.timeDifference("2018-11-20 08:35:00","2018-11-20 08:35:00")).getStatus());
        Assert.assertEquals(2,TimeHandle.judgeStatus(TimeHandle.timeDifference("2018-11-20 08:42:00","2018-11-20 08:35:00")).getStatus());
        Assert.assertEquals(3,TimeHandle.judgeStatus(TimeHandle.timeDifference("2018-11-20 09:04:00","2018-11-20 08:35:00")).getStatus());
        Assert.assertEquals(4,TimeHandle.judgeStatus(TimeHandle.timeDifference("2018-11-20 09:06:00","2018-11-20 08:35:00")).getStatus());
        Status status = TimeHandle.judgeStatus(TimeHandle.timeDifference("2018-11-20 08:15:00","2018-11-20 08:35:00"));
        System.out.println(status.getStatus());
    }

    @Test
    public void testTimeDifference() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(TimeHandle.timeDifference(format.format(new Date()).substring(11,19),"08:30:00"));

        System.out.println("2018-11-05 08:12:58.0".substring(0,10));

        System.out.println(TimeHandle.timeDifference("08:20:00","08:35:00"));
        System.out.println(TimeHandle.timeDifference("2018-11-20 08:20:00","2018-11-20 08:35:00"));
        System.out.println(TimeHandle.timeDifference("18:00:00","18:01:00"));
        System.out.println(TimeHandle.timeDifference("18:00:00","17:01:00"));
    }

}
