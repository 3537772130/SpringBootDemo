package com.example.demo.controller.manager;

import com.example.demo.entity.ViewAppletAudit;
import com.example.demo.entity.ViewAppletAuditList;
import com.example.demo.entity.ViewAppletInfo;
import com.example.demo.service.AppletService;
import com.example.demo.util.AjaxResponse;
import com.example.demo.util.NullUtil;
import com.example.demo.util.Page;
import com.example.demo.util.PageUtil;
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
     * 分页查询小程序审核列表
     *
     * @param record
     * @param request
     * @return
     */
    @RequestMapping(value = "queryAppletAuditToPage")
    public Object queryAppletAuditToPage(ViewAppletAuditList record, HttpServletRequest request) {
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

}
