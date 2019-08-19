package com.example.demo.controller.user;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.AppletInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.AppletService;
import com.example.demo.service.ManagerService;
import com.example.demo.util.AjaxResponse;
import com.example.demo.util.Constants;
import com.example.demo.util.Page;
import com.example.demo.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: SpringBootDemo
 * @description: 用户小程序控制类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-08-19 09:36
 **/
@RestController
@RequestMapping(value = "/api/user/applet/")
public class UserAppletController {
    private static final Logger log = LoggerFactory.getLogger(UserAppletController.class);
    @Autowired
    private AppletService appletService;
    @Autowired
    private ManagerService managerService;

    /**
     * 查询小程序列表
     *
     * @param user
     * @param appletInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "selectAppletToPage")
    public Object selectAppletToPage(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, AppletInfo appletInfo, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        appletInfo.setUserId(user.getId());
        page = appletService.selectAppletInfoToPage(appletInfo, page);
        return AjaxResponse.success(page);
    }

    /**
     * 查询小程序信息详情
     *
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "selectAppletInfo")
    public Object selectAppletInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer id) {
        AppletInfo appletInfo = appletService.selectAppletInfo(id, user.getId());
        if (null == appletInfo) {
            return AjaxResponse.error("未找到相关信息");
        }
        return AjaxResponse.success(appletInfo);
    }
}
