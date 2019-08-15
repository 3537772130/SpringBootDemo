package com.example.demo.controller.web;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.ManagerInfo;
import com.example.demo.entity.RoleInfo;
import com.example.demo.entity.ViewManagerInfo;
import com.example.demo.service.ManagerService;
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
@RequestMapping(value = "/web/manager/")
public class ManagerController {
    private static final Logger log = LoggerFactory.getLogger(ManagerController.class);
    @Autowired
    private ManagerService managerService;

    /**
     * 加载用户主页
     *
     * @param managerInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "loadMain")
    public String loadMain(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo managerInfo, Model model) {
        try {
            if (null == managerInfo) {
                model.addAttribute("errorStr", "未获取到登录信息");
                return "/error";
            }
            RoleInfo role = managerService.selectRoleInfoById(managerInfo.getRoleId());
            model.addAttribute("manager", managerInfo);
            model.addAttribute("roleName", role.getRoleName());
            return "/user/main";
        } catch (Exception e) {
            log.error("加载主页出错：{}", e);
            model.addAttribute("errorStr", "加载出错");
            return "/error";
        }
    }

    /**
     * 加载管理员列表
     * @return
     */
    @RequestMapping(value = "loadManagerList")
    public String loadManagerList(){
        return "/manager/manager_list";
    }

    /**
     * 分页查询管理员信息集合
     * @param info
     * @param managerInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "selectUserInfoByPage")
    @ResponseBody
    public Object selectUserInfoByPage(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info,
                                       ViewManagerInfo managerInfo, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        if (info.getParentId() == 2){
            managerInfo.setParentId(info.getId());
        }
        page = managerService.selectManagerInfoByPage(managerInfo, page);
        return AjaxResponse.dataTables(page);
    }

    /**
     * 加载管理员信息
     * @param info
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "loadManagerInfo")
    public String loadManagerInfo(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info, Integer id, Model model){
        ManagerInfo managerInfo = managerService.selectManagerInfoById(id);
        if (info.getRoleId() == 2 && info.getId().intValue() != managerInfo.getParentId().intValue()){
            return "error";
        }
        model.addAttribute("manager", managerInfo.getManagerInfo(managerInfo));
        return "/manager/manager_info";
    }

    /**
     * 更新管理员信息
     * @param managerInfo
     * @return
     */
    @RequestMapping(value = "UpdateManagerInfo")
    @ResponseBody
    public Object UpdateManagerInfo(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info, ManagerInfo managerInfo) {
        if (null == managerInfo){
            return AjaxResponse.error("未获取到相关信息");
        }
        if (NullUtil.isNullOrEmpty(managerInfo.getId())) {
            if (NullUtil.isNullOrEmpty(managerInfo.getUserName())){
                return AjaxResponse.error("请设置账户");
            }
            if (NullUtil.isNullOrEmpty(managerInfo.getPassword())) {
                return AjaxResponse.error("请设置密码");
            }
        } else if (NullUtil.isNotNullOrEmpty(managerInfo.getUserName())){
            return AjaxResponse.error("禁止修改账户");
        }
        if (NullUtil.isNotNullOrEmpty(managerInfo.getPassword())) {
            managerInfo.setEncrypted(RandomUtil.getRandomStr32());
            String cipher = DesUtil.encrypt(managerInfo.getPassword(), managerInfo.getEncrypted());
            cipher = MD5Util.MD5(cipher);
            managerInfo.setPassword(cipher);
        }
        if (NullUtil.isNullOrEmpty(managerInfo.getNickName())) {
            return AjaxResponse.error("请设置昵称");
        }
        if (managerInfo.getNickName().getBytes().length > 20) {
            return AjaxResponse.error("昵称长度过长");
        }
        if (NullUtil.isNullOrEmpty(managerInfo.getMobile())) {
            return AjaxResponse.error("请设置手机号码");
        }
        if (!RegularUtil.checkMobile(managerInfo.getMobile())) {
            return AjaxResponse.error("手机号码格式不正确");
        }
        if (info.getRoleId() == 2){
            managerInfo.setParentId(info.getId());
            managerInfo.setRoleId(3);
        }
        try {
            managerService.updateManagerInfo(managerInfo);
            return AjaxResponse.success(NullUtil.isNullOrEmpty(managerInfo.getId()) ? "添加成功" : "更新成功");
        } catch (Exception e) {
            return AjaxResponse.error(NullUtil.isNullOrEmpty(managerInfo.getId()) ? "添加失败" : "更新失败");
        }
    }

    @RequestMapping(value = "loadSocket")
    public String loadSocket(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info, Model model) {
        model.addAttribute("sId", info.getId());
        return "/socket/socket";
    }

}
