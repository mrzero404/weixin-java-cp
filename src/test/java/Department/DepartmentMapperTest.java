package Department;

import com.github.binarywang.demo.wx.cp.WxCpDemoApplication;
import com.github.binarywang.demo.wx.cp.dao.CheckInOutMapper;
import com.github.binarywang.demo.wx.cp.dao.DepartmentMapper;
import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import com.github.binarywang.demo.wx.cp.entity.Department;
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
public class DepartmentMapperTest {

    @Resource
    private DepartmentMapper departmentMapper;

    @Test
    public void getWorkingHourByDepartment(){
        Department department = departmentMapper.getWorkingHourByDepartment(700187092);
        System.out.println(department.getWorkingHour());
        System.out.println(department.getOffWorkingHours());

    }
}
