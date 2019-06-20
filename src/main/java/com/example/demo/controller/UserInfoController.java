package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.*;
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
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/loadIndex")
    public String loadIndex(Model model){
        model.addAttribute("userId", 1);
        return "/index";
    }

    @RequestMapping(value = "/toMain")
    public String toMain(Integer userId, Model model){
        UserInfo userInfo = userInfoService.selectUserInfoById(userId);
        String userInfoJson = JSONObject.toJSON(userInfo).toString();
        model.addAttribute("userInfoJson", userInfoJson);
        model.addAttribute("userId", userId);
        Page page = userInfoService.selectUserInfo(null, new Page(0,10));
        model.addAttribute("list", page.getDataSource());
        return "/user/main";
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
