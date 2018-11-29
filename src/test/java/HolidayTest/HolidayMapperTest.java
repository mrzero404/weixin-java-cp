package HolidayTest;

import com.github.binarywang.demo.wx.cp.WxCpDemoApplication;
import com.github.binarywang.demo.wx.cp.dao.CheckInOutMapper;
import com.github.binarywang.demo.wx.cp.dao.HolidayMapper;
import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import com.github.binarywang.demo.wx.cp.entity.Holiday;
import dateTest.DateTest;
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
public class HolidayMapperTest {

    @Resource
    private HolidayMapper holidayMapper;

    @Test
    public void insertBatchHoliday(){
        int index = 0;
//        for (int i=1; i<=12; i++) {
//            List list = DateTest.getWeekendInMonth(2018, i);
//            List<Holiday> holidayList = new ArrayList<Holiday>();
//            for (Object s : list) {
//                index++;
//                Holiday holiday = new Holiday(index,s.toString(),0);
//                holidayList.add(holiday);
//            }
//            holidayMapper.insertBatchHoliday(holidayList);
//        }
    }

    @Test
    public void getHolidayByMonth() {
        List<String> holidayList = holidayMapper.getHolidayByMonth("2018-10");
        for (String holiday : holidayList) {
            System.out.println(holiday);
        }
    }
}
