package dateTest;

import com.github.binarywang.demo.wx.cp.WxCpDemoApplication;
import com.github.binarywang.demo.wx.cp.dao.CheckInOutMapper;
import com.github.binarywang.demo.wx.cp.dao.HolidayMapper;
import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/11/29 11:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WxCpDemoApplication.class)
public class DateUtil {

    @Resource
    private HolidayMapper holidayMapper;

    @Resource
    private CheckInOutMapper checkInOutMapper;

    public static void main(String[] args) {

    }

    @Test
    public void getStatus() {
        int[] total = new int[30];
        List<String> holidayList = holidayMapper.getHolidayByMonth("2018-11");
        for (String holiday : holidayList) {
            String day = holiday.substring(8,10);
            int index = 0;
            if (day.charAt(0)=='0'){
                index = Integer.valueOf(holiday.substring(9,10))-1;
            } else {
                index = Integer.valueOf(holiday.substring(8,10))-1;
            }
            total[index]= total[index]+1;
        }
        List<CheckInOut> checkInOutList = checkInOutMapper.getMinChecktimeBySSN("13143385664","2018-11");
        for (CheckInOut checkInOut : checkInOutList) {
            String day = checkInOut.getDatatime().substring(8,10);
            int index = 0;
            if (day.charAt(0)=='0'){
                index = Integer.valueOf(checkInOut.getDatatime().substring(9,10))-1;
            } else {
                index = Integer.valueOf(checkInOut.getDatatime().substring(8,10))-1;
            }
            total[index]= total[index]+1;
        }
        int j = 0;
        for (int i : total) {
            System.out.println(j++ +":"+i);
        }
    }

    @Test
    public void getd() {
        String s = "2018-11-07";
//        if (s.charAt(0)==0){
//            index = Integer.valueOf(checkInOut.getDatatime().substring(9,11));
//        } else {
//            index = Integer.valueOf(checkInOut.getDatatime().substring(8,11));
//        }
        System.out.println(s.substring(8,10));
        System.out.println(s.charAt(8)=='0');
        System.out.println(s.substring(9,10));
        System.out.println(s.charAt(9));
        String[] ss = s.split(" ");
        for (String sz : ss) {
            System.out.println(sz);
        }
    }

    @Test
    public void getMonthDay() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            calendar.setTime(sdf.parse("2018-11"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void format() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.parse("2018-11-01");
    }

}
