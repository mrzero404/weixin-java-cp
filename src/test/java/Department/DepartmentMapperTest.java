package Department;

import cn.com.lunaler.wx.cp.WxCpDemoApplication;
import cn.com.lunaler.wx.cp.dao.DepartmentMapper;
import cn.com.lunaler.wx.cp.entity.Department;
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
