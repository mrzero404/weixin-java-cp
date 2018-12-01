package checkInOutTest;

import cn.com.lunaler.wx.cp.WxCpDemoApplication;
import cn.com.lunaler.wx.cp.dao.CheckInOutMapper;
import cn.com.lunaler.wx.cp.entity.CheckInOut;
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
public class CheckInOutMapperTest {

    @Resource
    private CheckInOutMapper checkInOutMapper;

    @Test
    public void testJudgeStatus(){
//        Assert.assertEquals(15,checkInOutService.judgeState("2018-11-20 08:15:00","2018-11-20 08:30:00"));
//        System.out.println(checkInOutService.judgeState("2018-11-20 08:35:00","2018-11-20 08:30:00"));
//        checkInOutMapper.getChecktimeBySSN("13143385664","2018-11-");
        List<CheckInOut> minCheckInOutList = checkInOutMapper.getMinChecktimeBySSN("13143385664","2018-11-");
        List<CheckInOut> maxCheckInOutList = checkInOutMapper.getMaxChecktimeBySSN("13143385664","2018-11-");
        int index = 0;
        for(CheckInOut checkInOut : minCheckInOutList) {
            System.out.println("上班时间" + checkInOut.getDatatime());
            System.out.println("下班时间" + maxCheckInOutList.get(index).getDatatime());
            index ++;
        }
    }

    /**
     * 查询单个用户某一天/月/年的打卡时间
     */
    @Test
    public void getChecktimeByDate(){
        //一天
        List<CheckInOut> checkInOutByDayList = checkInOutMapper.getChecktimeByDate("13143385664","2018-11-01");
        for (CheckInOut checkInOut : checkInOutByDayList) {
            System.out.println(checkInOut.getDatatime());
        }
        //一月
        List<CheckInOut> checkInOutByMonthList = checkInOutMapper.getChecktimeByDate("13143385664","2018-11");
        for (CheckInOut checkInOut : checkInOutByMonthList) {
            System.out.println(checkInOut.getDatatime());
        }
        //一年
        List<CheckInOut> checkInOutByYearList = checkInOutMapper.getChecktimeByDate("13143385664","2018");
        for (CheckInOut checkInOut : checkInOutByYearList) {
            System.out.println(checkInOut.getDatatime());
        }
    }
}
