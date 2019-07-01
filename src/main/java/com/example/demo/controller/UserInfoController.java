package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
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
 * @description: test 控制类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-06-14 14:50
 **/
@Controller
@RequestMapping(value = "/user")
public class UserInfoController {
    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 加载index页面
     * @return
     */
    @RequestMapping(value = "/toIndex")
    public String toIndex(){
        return "/index";
    }

    /**
     * 初始化登录界面
     * @return
     */
    @RequestMapping(value = "/loadLogin")
    public String loadLogin(){
        return "/user/login";
    }

    /**
     * 登录校验
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public Object doLogin(String userName, String password, HttpServletRequest request){
        try {
            if (NullUtil.isNullOrEmpty(userName)){
                return AjaxResponse.error("用户名不能为空");
            }
            if (NullUtil.isNullOrEmpty(password)){
                return AjaxResponse.error("密码不能为空");
            }
            UserInfo userInfo = userInfoService.selectUserInfoByUserName(userName);
            if (null == userInfo){
                log.error("用户名不存在：{}", userName);
                return AjaxResponse.error("用户名或密码不匹配");
            }
            String cipher = DesUtil.encrypt(password, userInfo.getEncryptionStr());
            cipher = MD5Util.MD5(cipher);
            if (!cipher.equals(userInfo.getUserPass())){
                log.error("用户名：{}，输入的密码错误：{}", userName, password);
                return AjaxResponse.error("用户名或密码不匹配");
            }
            request.getSession().setAttribute(Constants.WEB_USER_INFO, SerializeUtil.serialize(userInfo));
            return AjaxResponse.success("登录成功");
        } catch (Exception e) {
            log.error("登录出错：{}", e);
            return AjaxResponse.error("登录失败");
        }
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/exitLogin")
    @ResponseBody
    public Object exitLogin(HttpServletRequest request){
        try {
            request.getSession().removeAttribute(Constants.WEB_USER_INFO);
            return AjaxResponse.success("退出成功");
        } catch (Exception e) {
            log.error("退出登录出错：{}", e);
            return AjaxResponse.error("退出登录失败");
        }
    }

    /**
     * 加载用户主页
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/loadMain")
    public String loadMain(HttpServletRequest request, Model model) {
        try {
            UserInfo userInfo = (UserInfo) SerializeUtil.unserialize((byte[])request.getSession().getAttribute(Constants.WEB_USER_INFO));
            if (null == userInfo){
                model.addAttribute("errorStr", "未获取到登录信息");
                return "/error";
            }
            model.addAttribute("userName", userInfo.getUserName());
            return "/user/main";
        } catch (Exception e) {
            log.error("加载主页出错：{}", e);
            model.addAttribute("errorStr", "加载出错");
            return "/error";
        }
    }

    @RequestMapping(value = "/addOrUpdateUserInfo")
    @ResponseBody
    public Object addOrUpdateUserInfo(UserInfo userInfo){
        if (NullUtil.isNullOrEmpty(userInfo.getId())){
            if (NullUtil.isNullOrEmpty(userInfo.getUserName())){
                return AjaxResponse.error("请设置账户");
            }
            if (!RegularUtil.checkMobile(userInfo.getUserName())){
                return AjaxResponse.error("账户格式不正确");
            }
            if (NullUtil.isNullOrEmpty(userInfo.getUserPass())){
                return AjaxResponse.error("请设置密码");
            }
            userInfo.setEncryptionStr(RandomUtil.getRandomStr32());
        }
        if (NullUtil.isNullOrEmpty(userInfo.getNickName())){
            return AjaxResponse.error("请设置昵称");
        }
        try {
            userInfoService.addOrUpdateUserInfo(userInfo);
            return AjaxResponse.success(NullUtil.isNullOrEmpty(userInfo.getId()) ? "添加成功":"更新成功");
        } catch (Exception e) {
            return AjaxResponse.error(NullUtil.isNullOrEmpty(userInfo.getId()) ? "添加失败":"更新失败");
        }
    }

    @RequestMapping(value = "selectUserInfoByPage")
    @ResponseBody
    public Object selectUserInfoByPage(String userName, HttpServletRequest request){
        Page page = PageUtil.initPage(request);
        page = userInfoService.selectUserInfo(userName, page);
        return AjaxResponse.dataTables(page);
    }
}
