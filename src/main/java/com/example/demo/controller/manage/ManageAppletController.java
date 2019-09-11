package com.example.demo.controller.manage;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.*;
import com.example.demo.service.AppletService;
import com.example.demo.util.*;
import com.example.demo.util.encryption.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: SpringBootDemo
 * @description: 管理小程序控制类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-08-19 13:26
 **/
@RestController
@RequestMapping(value = "/api/manage/applet/")
public class ManageAppletController {
    private static final Logger log = LoggerFactory.getLogger(ManageAppletController.class);
    @Autowired
    private AppletService appletService;

    /**
     * 分页查询小程序列表
     *
     * @param info
     * @param request
     * @return
     */
    @RequestMapping(value = "queryAppletToPage")
    public Object queryAppletToPage(ViewAppletInfo info, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        page = appletService.selectAppletInfoToPage(info, page);
        return AjaxResponse.success(page);
    }

    /**
     * 加载小程序信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "loadAppletDetails")
    public Object loadAppletDetails(Integer id) {
        if (NullUtil.isNullOrEmpty(id)) {
            return AjaxResponse.error("参数错误");
        }
        ViewAppletInfo info = appletService.selectAppletInfo(id);
        if (null == info) {
            return AjaxResponse.error("未找到相关信息");
        }
        return AjaxResponse.success();
    }

    /**
     * 分页查询小程序审核列表(初审)
     *
     * @param record
     * @param request
     * @return
     */
    @RequestMapping(value = "queryAppletAuditToFirstTrial")
    public Object queryAppletAuditToFirstTrial(ViewAppletAuditList record, HttpServletRequest request) {
        record.setAuditResult(0);
        Page page = PageUtil.initPage(request);
        page = appletService.selectAppletAuditToPage(record, page);
        return AjaxResponse.success(page);
    }

    /**
     * 分页查询小程序审核列表(终审)
     *
     * @param record
     * @param request
     * @return
     */
    @RequestMapping(value = "queryAppletAuditToLastTrial")
    public Object queryAppletAuditToLastTrial(ViewAppletAuditList record, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        page = appletService.selectAppletAuditToPage(record, page);
        return AjaxResponse.success(page);
    }

    /**
     * 查询小程序审核记录
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "queryAppletAuditToList")
    public Object queryAppletAuditToList(Integer id) {
        List<ViewAppletAudit> list = appletService.selectAppletAuditList(id);
        return NullUtil.isNotNullOrEmpty(list) ? AjaxResponse.success(list) : AjaxResponse.error("未找到相关记录");
    }

    /**
     * 加载小程序审核信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "loadAppletAuditDetails")
    public Object loadAppletAuditDetails(Integer id) throws Exception {
        if (NullUtil.isNullOrEmpty(id)) {
            return AjaxResponse.error("参数错误");
        }
        ViewAppletAuditList record = appletService.selectAppletAuditDetails(id);
        if (null == record) {
            return AjaxResponse.error("未找到相关信息");
        }
        record.setManagerAccount(EncryptionUtil.decryptAppletRSA(record.getManagerAccount()));
        record.setManagerPassword(EncryptionUtil.decryptAppletRSA(record.getManagerPassword()));
        record.setAppId(EncryptionUtil.decryptAppletRSA(record.getAppId()));
        record.setAppSecret(EncryptionUtil.decryptAppletRSA(record.getAppSecret()));
        return AjaxResponse.success(record);
    }

    /**
     * 添加小程序审核信息
     *
     * @param manager
     * @param appletAudit
     * @return
     */
    @RequestMapping(value = "saveAppletAuditInfo")
    public Object saveAppletAuditInfo(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo manager, AppletAudit appletAudit) {
        try {
            if (null == appletAudit) {
                return AjaxResponse.error("获取参数出错");
            }
            if (NullUtil.isNullOrEmpty(appletAudit.getAppletId())) {
                return AjaxResponse.error("参数错误");
            }
            if (NullUtil.isNullOrEmpty(appletAudit.getResult())) {
                return AjaxResponse.error("请选择审核结果");
            }
            if (NullUtil.isNullOrEmpty(appletAudit.getRemark())) {
                return AjaxResponse.error("请填写备注说明");
            }
            ViewAppletAudit audit = appletService.selectAppletNewAudit(appletAudit.getAppletId());
            if (null == audit) {
                return AjaxResponse.error("参数错误，未获取到相关信息");
            }
            if (audit.getAuditResult().intValue() == -1 || audit.getAuditResult().intValue() == 2) {
                return AjaxResponse.error("小程序状态不符");
            }
            appletAudit.setAppletCode(audit.getAppletCode());
            appletAudit.setAuditorId(manager.getId());
            if (appletAudit.getResult().intValue() == 1 && audit.getAuditResult().intValue() == 1) {
                appletAudit.setResult(2);
            }
            appletService.addAppletAudit(appletAudit);
            return AjaxResponse.success("提交成功");
        } catch (Exception e) {
            log.error("提交小程序审核信息出错{}", e);
            return AjaxResponse.error("提交失败");
        }
    }
}
