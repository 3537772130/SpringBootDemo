package com.example.demo.controller.user;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.*;
import com.example.demo.service.AppletService;
import com.example.demo.service.ManagerService;
import com.example.demo.util.*;
import com.example.demo.util.encryption.EncryptionUtil;
import com.example.demo.util.qiniu.QiNiuUtil;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
     * 查询用户小程序审核列表
     *
     * @param user
     * @param record
     * @param request
     * @return
     */
    @RequestMapping(value = "queryAppletAuditToPage")
    public Object queryAppletAuditToPage(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, ViewAppletAuditList record, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        record.setUserId(user.getId());
        page = appletService.selectAppletAuditToPage(record, page);
        return AjaxResponse.success(page);
    }

    /**
     * 查询小程序列表
     *
     * @param user
     * @param appletInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "queryAppletToPage")
    public Object queryAppletToPage(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, ViewAppletInfo appletInfo, HttpServletRequest request) {
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
    @RequestMapping(value = "queryAppletInfo")
    public Object queryAppletInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer id) {
        Map map = new HashMap<>();
        map.put("regions", new JSONArray(Constants.REGION_MAP_TO_NAME).toString());
        map.put("typeList", appletService.selectAppletTypeList(true));
        if (NullUtil.isNotNullOrEmpty(id) && id.intValue() != 0) {
            try {
                ViewAppletInfo appletInfo = appletService.selectAppletInfo(id, user.getId());
                if (null != appletInfo) {
                    appletInfo.setUserId(null);
                    appletInfo.setAddressSimple(null);
                    appletInfo.setAddressDetails(null);
                    appletInfo.setLat(null);
                    appletInfo.setLon(null);
                    appletInfo.setIfSelling(null);
                    appletInfo.setStatus(null);
                    appletInfo.setManagerAccount(EncryptionUtil.decryptAppletRSA(appletInfo.getManagerAccount()));
                    appletInfo.setManagerPassword(EncryptionUtil.decryptAppletRSA(appletInfo.getManagerPassword()));
                    appletInfo.setAppId(EncryptionUtil.decryptAppletRSA(appletInfo.getAppId()));
                    appletInfo.setAppSecret(EncryptionUtil.decryptAppletRSA(appletInfo.getAppSecret()));
                    map.put("applet", appletInfo);
                    return AjaxResponse.success(map);
                }
            } catch (Exception e) {
                log.error("用户获取小程序信息出错{}", e);
            }
        }
        return AjaxResponse.msg("-1", map);
    }

    /**
     * 查询小程序详情
     *
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "queryAppletDetails")
    public Object queryAppletDetails(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer id) {
        try {
            ViewAppletInfo appletInfo = appletService.selectAppletInfo(id, user.getId());
            if (null != appletInfo) {
                appletInfo.setUserId(null);
                appletInfo.setManagerAccount(EncryptionUtil.decryptAppletRSA(appletInfo.getManagerAccount()));
                appletInfo.setManagerPassword(EncryptionUtil.decryptAppletRSA(appletInfo.getManagerPassword()));
                appletInfo.setAppId(EncryptionUtil.decryptAppletRSA(appletInfo.getAppId()));
                appletInfo.setAppSecret(EncryptionUtil.decryptAppletRSA(appletInfo.getAppSecret()));
                return AjaxResponse.success(appletInfo);
            }
        } catch (Exception e) {
            log.error("查询小程序详情出错{}", e);
        }
        return AjaxResponse.error("未找到相关记录");
    }


    /**
     * 上传小程序头像到草稿
     *
     * @param user
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "uploadAppletLogo")
    public Object uploadAppletLogo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, @RequestParam("appletLogo") MultipartFile multipartFile) {
        try {
            //校验文件信息
            CheckResult result = CheckFileUtil.checkImageFile(multipartFile);
            if (!result.getBool()) {
                return AjaxResponse.error(result.getMsg());
            }
            String fileKey = "/api/public/U" + user.getId() + "-AL" + RandomUtil.getTimeStamp();
            QiNiuUtil.uploadFile(multipartFile, fileKey);
            return AjaxResponse.success(fileKey);
        } catch (Exception e) {
            log.error("上传头像出错{}", e);
            return AjaxResponse.success("上传失败");
        }
    }

    /**
     * 上传小程序执照许可到草稿
     *
     * @param user
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "uploadAppletLicense")
    public Object uploadAppletLicense(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, @RequestParam("appletLicense") MultipartFile multipartFile) {
        try {
            //校验文件信息
            CheckResult result = CheckFileUtil.checkImageFile(multipartFile);
            if (!result.getBool()) {
                return AjaxResponse.error(result.getMsg());
            }
            String fileKey = NullUtil.isNotNullOrEmpty(user.getAvatarUrl()) ?
                    user.getAvatarUrl() : "/api/public/U" + user.getId() + "-AL" + RandomUtil.getTimeStamp();
            QiNiuUtil.uploadFile(multipartFile, fileKey);
            return AjaxResponse.success(fileKey);
        } catch (Exception e) {
            log.error("上传头像出错{}", e);
            return AjaxResponse.success("上传失败");
        }
    }

    /**
     * 更新小程序信息
     *
     * @param user
     * @param appletInfo
     * @return
     */
    @RequestMapping(value = "saveAppletInfo")
    public Object saveAppletInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, AppletInfo appletInfo) {
        try {
            if (null == appletInfo) {
                return AjaxResponse.error("提交出错");
            }
            if (NullUtil.isNotNullOrEmpty(appletInfo.getId())) {
                ViewAppletInfo info = appletService.selectAppletInfo(appletInfo.getId(), user.getId());
                if (null == info) {
                    return AjaxResponse.success("您没有权限修改");
                }
                appletInfo.setUserId(user.getId());
                appletInfo.setAppletCode(info.getAppletCode());
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
            if (appletInfo.getBusinessScope().trim().length() > 500) {
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

            String appletLogo = "/api/image/LOGO-" + RandomUtil.getTimeStamp();
            String licenseSrc = "/api/image/LICENSE-" + RandomUtil.getTimeStamp();
            if (NullUtil.isNotNullOrEmpty(appletInfo.getId())) {
                AppletInfo oldInfo = appletService.selectAppletInfoById(appletInfo.getId());
                appletLogo = oldInfo.getAppletLogo().equals(appletInfo.getAppletLogo()) ? null : oldInfo.getAppletLogo();
                licenseSrc = oldInfo.getLicenseSrc().equals(appletInfo.getLicenseSrc()) ? null : oldInfo.getLicenseSrc();
            }
            if (NullUtil.isNotNullOrEmpty(appletLogo)) {
                QiNiuUtil.removeFile(appletInfo.getAppletLogo(), appletLogo);
                appletInfo.setAppletLogo(appletLogo);
            }
            if (NullUtil.isNotNullOrEmpty(licenseSrc)) {
                QiNiuUtil.removeFile(appletInfo.getLicenseSrc(), licenseSrc);
                appletInfo.setLicenseSrc(licenseSrc);
            }

            // 保存小程序信息
            appletService.saveAppletInfo(appletInfo);
            return AjaxResponse.success("提交成功");
        } catch (Exception e) {
            log.error("提交小程序信息出错{}", e);
            return AjaxResponse.error("提交失败");
        }
    }

}
