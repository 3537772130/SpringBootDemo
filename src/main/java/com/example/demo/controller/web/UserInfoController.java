package com.example.demo.controller.web;

import com.example.demo.config.annotation.SessionScope;
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
@RequestMapping(value = "/web/user/")
public class UserInfoController {
    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 加载用户主页
     *
     * @param userInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "loadMain")
    public String loadMain(@SessionScope(Constants.WEB_USER_INFO) UserInfo userInfo, Model model) {
        try {
            if (null == userInfo) {
                model.addAttribute("errorStr", "未获取到登录信息");
                return "/error";
            }
//            log.info("nickName[byte]长度为：{}", userInfo.getNickName().getBytes().length);
            model.addAttribute("user", userInfo);
            return "/user/main";
        } catch (Exception e) {
            log.error("加载主页出错：{}", e);
            model.addAttribute("errorStr", "加载出错");
            return "/error";
        }
    }

    @RequestMapping(value = "addOrUpdateUserInfo")
    @ResponseBody
    public Object addOrUpdateUserInfo(UserInfo userInfo) {
        if (NullUtil.isNullOrEmpty(userInfo.getId())) {
            if (NullUtil.isNullOrEmpty(userInfo.getUserName())) {
                return AjaxResponse.error("请设置账户");
            }
            if (!RegularUtil.checkMobile(userInfo.getUserName())) {
                return AjaxResponse.error("账户格式不正确");
            }
            if (NullUtil.isNullOrEmpty(userInfo.getUserPass())) {
                return AjaxResponse.error("请设置密码");
            }
            userInfo.setEncryptionStr(RandomUtil.getRandomStr32());
        }
        if (NullUtil.isNullOrEmpty(userInfo.getNickName())) {
            return AjaxResponse.error("请设置昵称");
        }
        if (userInfo.getNickName().getBytes().length > 20) {
            return AjaxResponse.error("昵称长度过长");
        }
        try {
            userInfoService.addOrUpdateUserInfo(userInfo);
            return AjaxResponse.success(NullUtil.isNullOrEmpty(userInfo.getId()) ? "添加成功" : "更新成功");
        } catch (Exception e) {
            return AjaxResponse.error(NullUtil.isNullOrEmpty(userInfo.getId()) ? "添加失败" : "更新失败");
        }
    }

    @RequestMapping(value = "selectUserInfoByPage")
    @ResponseBody
    public Object selectUserInfoByPage(String userName, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        page = userInfoService.selectUserInfo(userName, page);
        return AjaxResponse.dataTables(page);
    }

    @RequestMapping(value = "loadSocket")
    public String loadSocket(@SessionScope(Constants.WEB_USER_INFO) UserInfo info, Model model) {
        model.addAttribute("sId", info.getId());
        return "/socket/socket";
    }

}
