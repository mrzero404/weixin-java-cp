package HolidayTest;

import cn.com.lunaler.wx.cp.WxCpDemoApplication;
import cn.com.lunaler.wx.cp.dao.HolidayMapper;
import cn.com.lunaler.wx.cp.entity.Holiday;
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
        for (int i=1; i<=12; i++) {
            List list = DateTest.getWeekendInMonth(2018, i);
            List<Holiday> holidayList = new ArrayList<Holiday>();
            for (Object s : list) {
                index++;
                Holiday holiday = null;
                if ((index+1)%4==0) {
                    holiday = new Holiday(index,s.toString(),0,0,1);
                } else {
                    holiday = new Holiday(index,s.toString(),0,0,0);

                }
                holidayList.add(holiday);
            }
            holidayMapper.insertBatchHoliday(holidayList);
        }
    }

    /**
     * 插入一个节假日
     */
    @Test
    public void insertHoliday() {
        Holiday holiday = new Holiday(1,"2018-01-01", 0, 1, 0);
        System.out.println(holidayMapper.insertHoliday(holiday));
    }


    /**
     * 查询一个月中的周末日期
     */
    @Test
    public void getHolidayByMonth() {
        List<String> holidayList = holidayMapper.getHolidayByMonth("2018-10");
        for (String holiday : holidayList) {
            System.out.println(holiday);
        }
    }

    /**
     * 更新节假日与周末重合的日期
     */
    @Test
    public void updateHoliday() {

        //更新成功返回1
        Holiday holiday = new Holiday(1,"2018-01-06", 0, 1, 0);
        System.out.println(holidayMapper.updateHoliday(holiday));

        //更新没有在表里的日期失败返回0
        Holiday holidayError = new Holiday(1,"2018-01-05", 0, 1, 0);
        System.out.println(holidayMapper.updateHoliday(holidayError));
    }

    /**
     * 查询所有节假日缓存表合并到非工作日表中
     */
    @Test
    public void getAllHolidayCache() {
        List<Holiday> holidayList = holidayMapper.getAllHolidayCache();
        for (Holiday holiday : holidayList) {
            if (holidayMapper.isExist(holiday.getHoliday()) == 1) {
                if (holiday.getIsHoliday() == 1) {
                    holidayMapper.updateHoliday(holiday);
                } else if (holiday.getIsWork() == 1){
                    holidayMapper.updateHoliday(holiday);
                }
            } else {
                holidayMapper.insertHoliday(holiday);
            }
            System.out.println(holiday.getHoliday()+":"+holidayMapper.isExist(holiday.getHoliday()));
        }
    }
}
