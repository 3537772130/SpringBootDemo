package com.example.demo.controller.manager;

import com.example.demo.config.annotation.CancelAuthentication;
import com.example.demo.entity.ManagerInfo;
import com.example.demo.entity.ViewManagerInfo;
import com.example.demo.service.ManagerService;
import com.example.demo.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: SpringBootDemo
 * @description: web登录控制层
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-17 11:03
 **/
@RestController
@RequestMapping(value = "/api/manage/")
public class ManagerLoginController {
    private static final Logger log = LoggerFactory.getLogger(ManagerLoginController.class);
    @Autowired
    private ManagerService managerService;

    /**
     * 登录校验
     *
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "doLogin")
    @CancelAuthentication
    public Object doLogin(String userName, String password, HttpServletRequest request) {
        try {
            if (NullUtil.isNullOrEmpty(userName)) {
                return AjaxResponse.error("用户名不能为空");
            }
            if (NullUtil.isNullOrEmpty(password)) {
                return AjaxResponse.error("密码不能为空");
            }
            ViewManagerInfo managerInfo = managerService.selectManagerInfoByUserName(userName);
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
            if (managerInfo.getStatus().intValue() == 0) {
                return AjaxResponse.error("该账户已禁用");
            }
            ManagerInfo manager = managerService.selectManagerInfoById(managerInfo.getId());
            request.getSession().setAttribute(Constants.WEB_MANAGER_INFO, SerializeUtil.serialize(manager.getManagerInfo(manager)));
            return AjaxResponse.success(managerInfo.getManagerInfo(managerInfo));
        } catch (Exception e) {
            log.error("登录出错：{}", e);
            return AjaxResponse.error("登录失败");
        }
    }

    /**
     * 检查登录状态
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "checkLogin")
    @CancelAuthentication
    public Object checkLogin(HttpServletRequest request) {
        ManagerInfo manager = (ManagerInfo) SerializeUtil.unserialize((byte[]) request.getSession().getAttribute(Constants.WEB_MANAGER_INFO));
        if (null == manager) {
            return AjaxResponse.error("请先登录");
        }
        return AjaxResponse.success();
    }

    /**
     * 检测账号是否已注册
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "checkUserNameRegistered")
    @CancelAuthentication
    public Object checkUserNameRegistered(String userName) {
        ViewManagerInfo info = managerService.selectManagerInfoByUserName(userName);
        if (null == info) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error("该账户已被注册");
    }

}
