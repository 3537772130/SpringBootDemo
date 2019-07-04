package com.example.demo.controller.vue;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
     * 登录校验
     *
     * @param info
     * @param request
     * @return
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public Object doLogin(UserInfo info, HttpServletRequest request) {
        try {
            if (NullUtil.isNullOrEmpty(info.getUserName())) {
                return AjaxResponse.error("用户名不能为空");
            }
            if (NullUtil.isNullOrEmpty(info.getUserPass())) {
                return AjaxResponse.error("密码不能为空");
            }
            UserInfo userInfo = userInfoService.selectUserInfoByUserName(info.getUserName());
            if (null == userInfo) {
                log.error("用户名不存在：{}", info.getUserName());
                return AjaxResponse.error("用户名或密码不匹配");
            }
            String cipher = DesUtil.encrypt(info.getUserPass(), userInfo.getEncryptionStr());
            cipher = MD5Util.MD5(cipher);
            if (!cipher.equals(userInfo.getUserPass())) {
                log.error("用户名：{}，输入的密码错误：{}", info.getUserName(), info.getUserPass());
                return AjaxResponse.error("用户名或密码不匹配");
            }
            request.getSession().setAttribute(Constants.WEB_USER_INFO, SerializeUtil.serialize(userInfo));
            return AjaxResponse.success("登录成功");
        } catch (Exception e) {
            log.error("登录出错：{}", e);
            return AjaxResponse.error("登录失败");
        }
    }
}
