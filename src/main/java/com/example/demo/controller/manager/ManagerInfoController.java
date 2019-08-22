package com.example.demo.controller.manager;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.ManagerInfo;
import com.example.demo.entity.ManagerRole;
import com.example.demo.entity.RegionInfo;
import com.example.demo.entity.ViewManagerInfo;
import com.example.demo.service.ManagerService;
import com.example.demo.service.RegionService;
import com.example.demo.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: SpringBootDemo
 * @description: test 控制类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-06-14 14:50
 **/
@RestController
@RequestMapping(value = "/api/manage/")
public class ManagerInfoController {
    private static final Logger log = LoggerFactory.getLogger(ManagerInfoController.class);
    @Autowired
    private ManagerService managerService;
    @Autowired
    private RegionService regionService;

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "exitLogin")
    public Object exitLogin(HttpServletRequest request) {
        try {
            request.getSession().removeAttribute(Constants.WEB_MANAGER_INFO);
            return AjaxResponse.success("退出成功");
        } catch (Exception e) {
            log.error("退出登录出错：{}", e);
            return AjaxResponse.error("退出登录失败");
        }
    }

    /**
     * 分页查询管理员信息集合
     * @param info
     * @param managerInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "selectManagerInfoToPage")
    public Object selectManagerInfoToPage(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info,
                                       ViewManagerInfo managerInfo, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        if (NullUtil.isNotNullOrEmpty(info.getParentId()) && info.getParentId() == 3) {
            managerInfo.setParentId(info.getId());
        }
        page = managerService.selectManagerInfoToPage(managerInfo, page);
        return AjaxResponse.success(page);
    }

    /**
     * 获取管理员信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getManagerInfo")
    public Object getManagerInfo(Integer id) {
        ManagerInfo managerInfo = managerService.selectManagerInfoById(id);
        if (null != managerInfo) {
            managerInfo.setPassword(null);
            managerInfo.setEncrypted(null);
            List<RegionInfo> cityList = regionService.selectRegionInfo(managerInfo.getProvince());
            List<RegionInfo> countyList = regionService.selectRegionInfo(managerInfo.getCity());
            Map map = new HashMap<>();
            map.put("manager", managerInfo);
            map.put("cityList", cityList);
            map.put("countyList", countyList);
            return AjaxResponse.success(map);
        }
        return AjaxResponse.error("未匹配到相关信息");
    }

    /**
     * 更新管理员信息
     * @param managerInfo
     * @return
     */
    @RequestMapping(value = "UpdateManagerInfo")
    public Object UpdateManagerInfo(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info, ManagerInfo managerInfo) {
        if (null == managerInfo){
            return AjaxResponse.error("未获取到相关信息");
        }
        // 当ID不为空时，是为修改，反之为新增
        if (NullUtil.isNullOrEmpty(managerInfo.getId())) {
            if (NullUtil.isNullOrEmpty(managerInfo.getUserName())){
                return AjaxResponse.error("请输入账号");
            }
            if (NullUtil.isNullOrEmpty(managerInfo.getPassword())) {
                return AjaxResponse.error("请输入密码");
            }
            // 进行修改权限校验
            ManagerInfo record = managerService.selectManagerInfoById(managerInfo.getId());
            if (info.getRoleId().intValue() != 1 && info.getId().intValue() != record.getParentId()) {
                return AjaxResponse.error("超出了您的权限");
            }
        } else if (NullUtil.isNotNullOrEmpty(managerInfo.getUserName())){
            return AjaxResponse.error("禁止修改账户");
        }
        if (NullUtil.isNotNullOrEmpty(managerInfo.getPassword())) {
            managerInfo.setEncrypted(RandomUtil.getRandomStr32());
            String cipher = DesUtil.encrypt(managerInfo.getPassword(), managerInfo.getEncrypted());
            cipher = MD5Util.MD5(cipher);
            managerInfo.setPassword(cipher);
        } else {
            managerInfo.setPassword(null);
        }
        if (NullUtil.isNullOrEmpty(managerInfo.getNickName())) {
            return AjaxResponse.error("请输入昵称");
        }
        if (managerInfo.getNickName().getBytes().length > 30) {
            return AjaxResponse.error("昵称长度过长");
        }
        if (NullUtil.isNullOrEmpty(managerInfo.getMobile())) {
            return AjaxResponse.error("请设置手机号码");
        }
        if (!RegularUtil.checkMobile(managerInfo.getMobile())) {
            return AjaxResponse.error("手机号码格式不正确");
        }
        if (info.getRoleId() == 3){
            managerInfo.setParentId(info.getId());
            managerInfo.setRoleId(3);
        }
        try {
            managerService.updateManagerInfo(managerInfo);
            return AjaxResponse.success("提交成功");
        } catch (Exception e) {
            return AjaxResponse.error("提交失败");
        }
    }

    /**
     * 查询角色集合
     *
     * @return
     */
    @RequestMapping(value = "selectManagerRoleList")
    public Object selectManagerRoleList() {
        return AjaxResponse.success(managerService.selectRoleToMap());
    }

    /**
     * 分页查询角色信息列表
     *
     * @param info
     * @param request
     * @return
     */
    @RequestMapping(value = "selectManagerRoleToPage")
    public Object selectManagerRoleToPage(ManagerRole info, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        page = managerService.selectRoleToPage(info, page);
        return AjaxResponse.success(page);
    }

    /**
     * 获取角色信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getManagerRole")
    public Object getManagerRole(Integer id) {
        ManagerRole managerRole = managerService.selectManagerRoleById(id);
        if (null != managerRole) {
            return AjaxResponse.success(managerRole);
        }
        return AjaxResponse.error("未匹配到相关信息");
    }

    /**
     * 更新角色信息
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "updateManagerRole")
    public Object updateManagerRole(ManagerRole info) {
        try {
            if (null == info) {
                return AjaxResponse.error("未获取到相关信息");
            }
            if (NullUtil.isNullOrEmpty(info.getRoleName())) {
                return AjaxResponse.error("请输入角色名称");
            }
            if (info.getRoleName().length() > 30) {
                return AjaxResponse.error("角色名称长度过长");
            }
            if (NullUtil.isNotNullOrEmpty(info.getDescribeStr()) && info.getDescribeStr().length() > 200) {
                return AjaxResponse.error("角色描述长度过长");
            }
            if (NullUtil.isNullOrEmpty(info.getStatus())) {
                info.setStatus(true);
            }
            managerService.updateManagerRole(info);
            return AjaxResponse.success("提交成功");
        } catch (Exception e) {
            log.error("提交管理员角色信息出错{}", e);
            return AjaxResponse.error("提交失败");
        }
    }
}
