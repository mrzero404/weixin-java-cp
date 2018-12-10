package cn.com.lunaler.wx.cp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/12/6 15:04
 */
public class DateUtil {


    /**
     * 获取两个日期之间的日期
     * @param dates 包含开始日期与结束日期的字符串数组
     * @return 日期集合
     */
    public static List<String> getBetweenDates(List<String> dates) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
        Date start = null;
        Date end = null;
        try {
            start = df.parse(dates.get(0));
            end = df.parse(dates.get(1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> result = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.add(tempEnd.DATE,1);
        while (tempStart.before(tempEnd)) {
            result.add(df.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }
}
