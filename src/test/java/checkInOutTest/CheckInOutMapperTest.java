package checkInOutTest;

import com.github.binarywang.demo.wx.cp.WxCpDemoApplication;
import com.github.binarywang.demo.wx.cp.dao.CheckInOutMapper;
import com.github.binarywang.demo.wx.cp.service.CheckInOutService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author LinWeiYu
 * @date 2018/11/21 9:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WxCpDemoApplication.class)
public class CheckInOutMapperTest {

    @Resource
    private CheckInOutMapper checkInOutMapper;

    @Test
    public void testJudgeStatus(){
//        Assert.assertEquals(15,checkInOutService.judgeState("2018-11-20 08:15:00","2018-11-20 08:30:00"));
//        System.out.println(checkInOutService.judgeState("2018-11-20 08:35:00","2018-11-20 08:30:00"));
//        checkInOutMapper.getChecktimeBySSN("13143385664","2018-11-");
        checkInOutMapper.getChecktimeBySSN("13143385664","2018-11-");

    }
}
