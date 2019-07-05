package com.example.demo.controller.vue;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.AjaxResponse;
import com.example.demo.util.Constants;
import com.example.demo.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: SpringBootDemo
 * @description: vue用户信息控制层
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-03 16:03
 **/
@RestController
@RequestMapping(value = "/api/user")
public class VueUserInfoController {
    private static final Logger log = LoggerFactory.getLogger(VueUserInfoController.class);
    @Autowired
    public UserInfoService userInfoService;

    /**
     * 获取登录信息
     * @param request
     * @return
     */
    @RequestMapping(value = "getUserInfo")
    public Object getUserInfo(HttpServletRequest request) {
        UserInfo user = (UserInfo) SerializeUtil.unserialize((byte[]) request.getSession().getAttribute(Constants.WEB_USER_INFO));
        if (null == user) {
            return AjaxResponse.error("请先登录");
        }
        Map map = new HashMap<>();
        map.put("userName", user.getUserName());
        map.put("nickName", user.getNickName());
        return AjaxResponse.success(map);
    }
}
