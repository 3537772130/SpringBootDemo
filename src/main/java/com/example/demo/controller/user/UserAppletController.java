package com.example.demo.controller.user;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.AppletInfo;
import com.example.demo.entity.ManagerInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.ViewAppletInfo;
import com.example.demo.service.AppletService;
import com.example.demo.service.ManagerService;
import com.example.demo.util.*;
import com.example.demo.util.encryption.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ViewAppletInfo appletInfo = null;
        if (NullUtil.isNotNullOrEmpty(id)) {
            appletInfo = appletService.selectAppletInfo(id, user.getId());
        } else {
            appletInfo = appletService.selectAppletInfo(user.getId());
        }
        if (null == appletInfo) {
            return AjaxResponse.error("未找到相关信息");
        }
        return AjaxResponse.success(appletInfo);
    }

    /**
     * 更新小程序信息
     *
     * @param user
     * @param appletInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "updateAppletInfo")
    public Object updateAppletInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, AppletInfo appletInfo, HttpServletRequest request) {
        try {
            if (null == appletInfo) {
                return AjaxResponse.error("提交出错");
            }
            if (NullUtil.isNotNullOrEmpty(appletInfo.getId())) {
                ViewAppletInfo info = appletService.selectAppletInfo(appletInfo.getId(), user.getId());
                if (null == info) {
                    return AjaxResponse.success("您没有权限修改");
                } else if (info.getIfComplete()) {
                    appletInfo.setRecommenderId(null);
                }
                appletInfo.setUserId(null);
                appletInfo.setAppletCode(null);
                appletInfo.setStatus(null);
            } else {
                appletInfo.setUserId(user.getId());
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getAppletLogo())) {
                return AjaxResponse.error("请上传小程序LOGO");
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getAppletName())) {
                return AjaxResponse.error("小程序名称不能为空");
            }
            if (appletInfo.getAppletName().trim().length() > 200) {
                return AjaxResponse.error("小程序名称过长");
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getAppletSimple())) {
                return AjaxResponse.error("小程序简称不能为空");
            }
            if (appletInfo.getAppletSimple().trim().length() > 100) {
                return AjaxResponse.error("小程序简称过长");
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getLicenseSrc())) {
                return AjaxResponse.error("请上传营业执照");
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getLicenseCode())) {
                return AjaxResponse.error("执照代码不能为空");
            }
            if (appletInfo.getLicenseCode().trim().length() > 30) {
                return AjaxResponse.error("执照代码过长");
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getBusinessScope())) {
                return AjaxResponse.error("营业范围不能为空");
            }
            if (appletInfo.getBusinessScope().trim().length() > 200) {
                return AjaxResponse.error("营业范围过长");
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getProvince())) {
                return AjaxResponse.error("请选择省份");
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getCity())) {
                return AjaxResponse.error("请选择城市");
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getCounty())) {
                return AjaxResponse.error("请选择区县");
            }
            if (NullUtil.isNullOrEmpty(appletInfo.getManagerAccount())) {
                return AjaxResponse.error("管理账号不能为空");
            }
            if (appletInfo.getManagerAccount().trim().length() > 50) {
                return AjaxResponse.error("管理账号过长");
            }
            appletInfo.setManagerAccount(EncryptionUtil.encryptAppletRSA(appletInfo.getManagerAccount().trim()));
            if (NullUtil.isNullOrEmpty(appletInfo.getManagerPassword())) {
                return AjaxResponse.error("管理密码不能为空");
            }
            if (appletInfo.getManagerPassword().trim().length() > 50) {
                return AjaxResponse.error("管理密码过长");
            }
            appletInfo.setManagerPassword(EncryptionUtil.encryptAppletRSA(appletInfo.getManagerPassword().trim()));
            if (NullUtil.isNullOrEmpty(appletInfo.getAppId())) {
                return AjaxResponse.error("APPID不能为空");
            }
            if (appletInfo.getAppId().trim().length() > 30) {
                return AjaxResponse.error("APPID过长");
            }
            appletInfo.setAppId(EncryptionUtil.encryptAppletRSA(appletInfo.getAppId().trim()));
            if (NullUtil.isNullOrEmpty(appletInfo.getAppSecret())) {
                return AjaxResponse.error("SECRET不能为空");
            }
            if (appletInfo.getAppSecret().trim().length() > 150) {
                return AjaxResponse.error("SECRET过长");
            }
            appletInfo.setAppSecret(EncryptionUtil.encryptAppletRSA(appletInfo.getAppSecret()));
            if (NullUtil.isNotNullOrEmpty(appletInfo.getRecommenderId())) {
                ManagerInfo manager = managerService.selectManagerInfoById(appletInfo.getId());
                if (null == manager) {
                    return AjaxResponse.error("推荐人不存在");
                }
                if (!manager.getStatus()) {
                    return AjaxResponse.error("推荐码无效");
                }
            }

            appletService.updateAppletInfo(appletInfo);
            return AjaxResponse.success("提交成功");
        } catch (Exception e) {
            log.error("提交小程序信息出错{}", e);
            return AjaxResponse.error("提交失败");
        }
    }

    /**
     * 获取推荐人信息集
     *
     * @param extensionCode
     * @return
     */
    @RequestMapping(value = "getRecommenderMap")
    public Object getRecommenderMap(String extensionCode) {
        List<ManagerInfo> list = managerService.selectManagerInfoByExtensionCode(extensionCode);
        if (NullUtil.isNotNullOrEmpty(list)) {
            List<Map> mapList = new ArrayList<>();
            for (ManagerInfo info : list) {
                Map map = new HashMap();
                map.put("id", info.getId());
                map.put("code", info.getExtensionCode());
                map.put("name", info.getNickName());
                mapList.add(map);
            }
            return AjaxResponse.success(mapList);
        }
        return AjaxResponse.error("未找到相关信息");
    }
}
