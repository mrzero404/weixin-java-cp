package dateTest;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LinWeiYu
 * @date 2018/11/28 15:42
 */
public class DateTest {

    public static void main(String[] args) {
//        try {
//            System.out.println(isWeekend("2018/12/01"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        List<Date> dateList = getDayListOfMonth();
        dateList.remove(19);
        for (Date date : dateList) {
            System.out.println(date);
        }

        for (int i=1; i<=12; i++) {
            List list = getWeekendInMonth(2018,i);
            for (Object s : list) {
                System.out.println(s.toString());
            }

        }



    }

    public static String isWeekend(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        Date bdate = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return "OK";
        } else{
            return "NO";
        }
    }

    /**
     * java获取 当月所有的日期集合
     * @return
     */
    public static List<Date> getDayListOfMonth() {
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        String monthStr="0";
        if(month<10){
            monthStr="0"+month;
        }else{
            monthStr=String.valueOf(month);
        }
        for (int i = 1; i <= day; i++) {
            String days="0";
            if(i<10){
                days="0"+i;
            }else {
                days=String.valueOf(i);
            }
            String aDate = String.valueOf(year)+"-"+monthStr+"-"+days;
            SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sp.parse(aDate);
                list.add(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return list;
    }
    /**
     * 获取当月的所有周末
     * @param year
     * @param month
     * @return
     */
    public static List getWeekendInMonth(int year, int month) {
        List list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);// 不设置的话默认为当年
        if(month == 0) {
            month = calendar.get(Calendar.MONTH) + 1;
        }
        calendar.set(Calendar.MONTH, month - 1);// 设置月份
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为当月第一天
        int daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);// 当月最大天数
        for (int i = 0; i < daySize-1; i++) {
            calendar.add(Calendar.DATE, 1);//在第一天的基础上加1
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if (week == Calendar.SATURDAY || week == Calendar.SUNDAY) {// 1代表周日，7代表周六 判断这是一个星期的第几天从而判断是否是周末
                String months = null;
                String days = null;
                if (month<10) {
                    months = "0"+month;
                }else {
                    months = String.valueOf(month);
                }
                if (calendar.get(Calendar.DAY_OF_MONTH)<10) {
                    days = "0"+calendar.get(Calendar.DAY_OF_MONTH);
                }else {
                    days = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                }
                list.add(year+"-"+months+"-"+days);// 得到当天是一个月的第几天
            }
        }
        return list;
    }

    private static List<Date> getDates(int year,int month){
        List<Date> dates = new ArrayList<Date>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH,  month - 1);
        cal.set(Calendar.DATE, 1);


        while(cal.get(Calendar.YEAR) == year &&
            cal.get(Calendar.MONTH) < month){
            int day = cal.get(Calendar.DAY_OF_WEEK);

            if(!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)){
                dates.add((Date)cal.getTime().clone());
            }
            cal.add(Calendar.DATE, 1);
        }
        return dates;

    }

}
