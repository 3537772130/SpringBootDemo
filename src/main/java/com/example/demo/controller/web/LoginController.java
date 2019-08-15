package com.example.demo.controller.web;

import com.example.demo.entity.ManagerInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.ViewManagerInfo;
import com.example.demo.service.ManagerService;
import com.example.demo.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: SpringBootDemo
 * @description: web登录控制层
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-17 11:03
 **/
@Controller
@RequestMapping(value = "/web/")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ManagerService managerService;

    /**
     * 加载index页面
     *
     * @return
     */
    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return "/index";
    }

    /**
     * 初始化登录界面
     *
     * @return
     */
    @RequestMapping(value = "loadLogin")
    public String loadLogin(HttpServletRequest request, Model model) {
        boolean loginStatus = false;
        Object obj = request.getSession().getAttribute("loginStatus");
        if (null != obj) {
            loginStatus = (boolean) obj;
            request.getSession().removeAttribute("loginStatus");
        }
        model.addAttribute("loginStatus", NullUtil.isNullOrEmpty(loginStatus) ? false : loginStatus);
        return "/user/login";
    }

    /**
     * 登录校验
     *
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "doLogin")
    @ResponseBody
    public Object doLogin(String userName, String password, HttpServletRequest request) {
        try {
            if (NullUtil.isNullOrEmpty(userName)) {
                return AjaxResponse.error("用户名不能为空");
            }
            if (NullUtil.isNullOrEmpty(password)) {
                return AjaxResponse.error("密码不能为空");
            }
            ManagerInfo managerInfo = managerService.selectManagerInfoByUserName(userName);
            if (null == managerInfo) {
                log.error("用户名不存在：{}", userName);
                return AjaxResponse.error("用户名或密码不匹配");
            }
            String cipher = DesUtil.encrypt(password, managerInfo.getEncrypted());
            cipher = MD5Util.MD5(cipher);
            if (!cipher.equals(managerInfo.getPassword())) {
                log.error("用户：{}，输入的密码错误：{}", userName, password);
                return AjaxResponse.error("用户名或密码不匹配");
            }
            request.getSession().setAttribute(Constants.WEB_MANAGER_INFO, SerializeUtil.serialize(managerInfo.getManagerInfo(managerInfo)));
            return AjaxResponse.success("登录成功");
        } catch (Exception e) {
            log.error("登录出错：{}", e);
            return AjaxResponse.error("登录失败");
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "exitLogin")
    @ResponseBody
    public Object exitLogin(HttpServletRequest request) {
        try {
            request.getSession().removeAttribute(Constants.WEB_MANAGER_INFO);
            return AjaxResponse.success("退出成功");
        } catch (Exception e) {
            log.error("退出登录出错：{}", e);
            return AjaxResponse.error("退出登录失败");
        }
    }
}
