package cn.com.lunaler.wx.cp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author LinWeiYu
 * @date 2018/12/4 16:50
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Staff {

    private long id ;

    private String label;

    private List<Staff> children;
}
