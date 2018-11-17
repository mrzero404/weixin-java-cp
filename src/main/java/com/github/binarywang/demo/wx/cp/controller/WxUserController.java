package com.github.binarywang.demo.wx.cp.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpOAuth2ServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.WxCpInMemoryConfigStorage;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Controller

public class WxUserController {
    @RequestMapping("/getCode")
    public String getCode(HttpServletRequest request, HttpServletResponse response) {

        String code = request.getParameter("code");
        System.out.println(code);
        WxCpInMemoryConfigStorage config = new WxCpInMemoryConfigStorage();
        config.setCorpId("wx3b60be9f2027ddbc");      // 设置微信企业号的appid
        config.setCorpSecret("peKuqFiWVgr4y-0o4q1gEfY16U4xHhQF9WI5VznAfzM");  // 设置微信企业号的app corpSecret
        config.setAgentId(1000004);     // 设置微信企业号应用ID

        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
        String userId = null;
        try {
            String[] res = wxCpService.getOauth2Service().getUserInfo(code);
            userId = res[0];
            String deviceId = res[1];
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        System.out.println(userId);
        return "calendar";
    }

    @RequestMapping("/getUser")
    void handleFoo(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3b60be9f2027ddbc&redirect_uri=http://my-domain.tunnel.qydev.com/getCode&response_type=code&scope=SCOPE&agentid=AGENTID&state=STATE");
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
