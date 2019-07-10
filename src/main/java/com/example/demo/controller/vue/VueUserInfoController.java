package com.example.demo.controller.vue;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.AjaxResponse;
import com.example.demo.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param user
     * @return
     */
    @RequestMapping(value = "getUserInfo")
    public Object getUserInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user) {
        if (null == user) {
            return AjaxResponse.error("请先登录");
        }
        return AjaxResponse.success(user);
    }
}
