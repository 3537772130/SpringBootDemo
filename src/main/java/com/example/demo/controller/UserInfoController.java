package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.AjaxResponse;
import com.example.demo.util.Page;
import com.example.demo.util.PageUtil;
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
@RequestMapping(value = "/test")
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

    @RequestMapping(value = "loadJson")
    @ResponseBody
    public Object loadJson(String userName, HttpServletRequest request){
        Page page = PageUtil.initPage(request);
        page = userInfoService.selectUserInfo(userName, page);
        return AjaxResponse.dataTables(page);
    }
}
