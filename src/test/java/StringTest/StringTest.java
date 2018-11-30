package StringTest;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LinWeiYu
 * @date 2018/11/29 21:13
 */
public class StringTest {
    public static void main(String[] args) {
        String s = "02";
        System.out.println(Integer.valueOf(s));
    }

    @Test
    public void testSubstring(){
        String s = "2018-11-01 08:07:16.0";
        System.out.println(s.substring(0,10));
    }

    /**
     * 迟到起算时间测试
     */
    @Test
    public void testLateTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = "08:56:00";
        Date date = sdf.parse(time);
        time = sdf.format(date.getTime()+300000);

        System.out.println(time);
        System.out.println(Integer.valueOf(time.substring(3,5))+5);
    }
}
