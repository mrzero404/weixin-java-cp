package com.github.binarywang.demo.wx.cp.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpConfiguration;
import com.github.binarywang.demo.wx.cp.dao.UserMapper;
import com.github.binarywang.demo.wx.cp.entity.CheckInOut;
import com.github.binarywang.demo.wx.cp.entity.CheckTime;
import com.github.binarywang.demo.wx.cp.service.CheckInOutService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpOAuth2ServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.config.WxCpInMemoryConfigStorage;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Controller

public class WxUserController {

    @Autowired
    private UserMapper userMapper;

//    @Autowired
//    private checkTimeMapper checkInOutMapper;

    @Autowired
    private CheckInOutService checkInOutService;

    private Map<String,String> userMap = new HashMap<String, String>();

    @RequestMapping("/getCode")
    public Object getCode(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String code = request.getParameter("code");
        WxCpService wxCpService = WxCpConfiguration.getCpServices().get(1000004);
        try {
            String[] res = wxCpService.getOauth2Service().getUserInfo(code);
            WxCpUser user = wxCpService.getUserService().getById(res[0]);
            userMap.put(request.getSession(true).getId(),user.getMobile());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "calendar";
    }

    @RequestMapping("/getPage")
    public Object getPage(){
        return "calendar";
    }

    @RequestMapping("/getUser")
    void handleFoo(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String seeionId = request.getSession(true).getId();
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3b60be9f2027ddbc&redirect_uri=http://my-domain.tunnel.qydev.com/getCode&response_type=code&scope=SCOPE&agentid=AGENTID&state=STATE");
    }

    @PostMapping(path = "/getCheckTime")
    public Object getcheckTime(@RequestBody Map<String, String> map, HttpServletRequest request){
        Map<String, Object> retMap = new HashMap<String, Object>();
        String SSN = userMap.get(request.getSession(true).getId());
        retMap.put("checkInOutList",checkInOutService.getCheckTimeByMonth(SSN,map.get("time")));
        return new ResponseEntity<Map<String, Object>>(retMap,HttpStatus.OK);
    }

    @PostMapping(path = "/getCheckTimeByDay")
    public Object getCheckTimeByDay(@RequestBody Map<String, String> map, HttpServletRequest request){
        Map<String, Object> retMap = new HashMap<String, Object>();
        String SSN = userMap.get(request.getSession(true).getId());
        System.out.println("**************************"+map.get("time"));
        retMap.put("checkInOutList",checkInOutService.getCheckTimeByMonth(SSN,map.get("time")));
        return new ResponseEntity<Map<String, Object>>(retMap,HttpStatus.OK);
    }
//    @RequestMapping("/getUser")
//    public String getUser() {
//        WxCpOAuth2ServiceImpl oAuth2Service = new WxCpOAuth2ServiceImpl(new WxCpServiceImpl());
//        Map<String, Object> retMap = new HashMap<String, Object>();
//        try {
//
//            HttpGet httpGet = new HttpGet(
//                "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3b60be9f2027ddbc&redirect_uri=http://hr.lunaler.com.cn:8080/getUser&response_type=code&scope=SCOPE&agentid=AGENTID&state=STATE");
//
//            HttpClient httpClient = HttpClients.createDefault();
//            HttpResponse res = httpClient.execute(httpGet);
//            HttpEntity entity = res.getEntity();
//            String result = EntityUtils.toString(entity, "UTF-8");
//            if (result != null) {
//                try {
//                    JSONObject oppidObj = JSONObject.fromObject(result);
//                    String code = (String) oppidObj.get("code");
//
//
//                    return code;
//                } catch (Exception e) {
//                    System.out.println("业务操作失败");
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("code无效");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }


}
