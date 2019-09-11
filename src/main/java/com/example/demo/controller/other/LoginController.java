package com.example.demo.controller.other;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.ManagerInfo;
import com.example.demo.entity.ViewManagerInfo;
import com.example.demo.service.ManagerService;
import com.example.demo.util.*;
import com.example.demo.util.encryption.DesUtil;
import com.example.demo.util.encryption.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: demo
 * @description: PC登陆控制层
 * @author: zhouhuahu
 * @create: 2019-08-17 16:27
 **/
@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ManagerService managerService;

    /**
     * 加载index页面
     *
     * @return
     */
    @RequestMapping(value = "index")
    public String toIndex() {
        return "/index";
    }

    /**
     * 初始化登录界面
     *
     * @return
     */
    @RequestMapping(value = "/pc/loadLogin")
    public String loadLogin(HttpServletRequest request, Model model) {
        boolean loginStatus = false;
        Object obj = request.getSession().getAttribute("loginStatus");
        if (null != obj) {
            loginStatus = (boolean) obj;
            request.getSession().removeAttribute("loginStatus");
        }
        model.addAttribute("loginStatus", NullUtil.isNullOrEmpty(loginStatus) ? false : loginStatus);
        return "/user/login";
    }

    /**
     * 登录校验
     *
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/pc/doLogin")
    @ResponseBody
    public Object doLogin(String userName, String password, HttpServletRequest request) {
        try {
            if (NullUtil.isNullOrEmpty(userName)) {
                return AjaxResponse.error("用户名不能为空");
            }
            if (NullUtil.isNullOrEmpty(password)) {
                return AjaxResponse.error("密码不能为空");
            }
            ViewManagerInfo managerInfo = managerService.selectManagerInfoByUserName(userName);
            if (null == managerInfo) {
                log.error("用户名不存在：{}", userName);
                return AjaxResponse.error("用户名或密码不匹配");
            }
            String cipher = DesUtil.encrypt(password, managerInfo.getEncrypted());
            cipher = MD5Util.MD5(cipher);
            if (!cipher.equals(managerInfo.getPassword())) {
                log.error("用户：{}，输入的密码错误：{}", userName, password);
                return AjaxResponse.error("用户名或密码不匹配");
            }
            if (managerInfo.getStatus().intValue() == 0) {
                return AjaxResponse.error("该账户已禁用");
            }
            ManagerInfo manager = managerService.selectManagerInfoById(managerInfo.getId());
            request.getSession().setAttribute(Constants.WEB_MANAGER_INFO, SerializeUtil.serialize(manager.getManagerInfo(manager)));
            return AjaxResponse.success(managerInfo.getManagerInfo(managerInfo));
        } catch (Exception e) {
            log.error("登录出错：{}", e);
            return AjaxResponse.error("登录失败");
        }
    }

    /**
     * 获取登录信息
     *
     * @param manager
     * @return
     */
    @RequestMapping(value = "/pc/getUserInfo")
    public Object getUserInfo(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo manager) {
        if (null == manager) {
            return AjaxResponse.error("请先登录");
        }
        return AjaxResponse.success(manager);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pc/exitLogin")
    @ResponseBody
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
     * 加载用户主页
     *
     * @param managerInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "/pc/manage/loadMain")
    public String loadMain(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo managerInfo, Model model) {
        try {
            if (null == managerInfo) {
                model.addAttribute("errorStr", "未获取到登录信息");
                return "/error";
            }
            model.addAttribute("manager", managerService.selectViewManagerInfoById(managerInfo.getId()));
            return "/user/main";
        } catch (Exception e) {
            log.error("加载主页出错：{}", e);
            model.addAttribute("errorStr", "加载出错");
            return "/error";
        }
    }

    /**
     * 加载账户信息
     *
     * @param info
     * @param model
     * @return
     */
    @RequestMapping(value = "/pc/manage/loadAccountInfo")
    public String loadAccountInfo(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info, Model model) {
        model.addAttribute("manager", info);
        return "/manage/account_info";
    }

    /**
     * 加载管理员列表
     *
     * @return
     */
    @RequestMapping(value = "/pc/manage/loadManagerList")
    public String loadManagerList() {
        return "/manage/manager_list";
    }

    /**
     * 分页查询管理员信息集合
     *
     * @param info
     * @param managerInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "/pc/manage/selectManagerInfoToPage")
    @ResponseBody
    public Object selectManagerInfoToPage(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info,
                                          ViewManagerInfo managerInfo, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        if (info.getParentId() == 3) {
            managerInfo.setParentId(info.getId());
        }
        page = managerService.selectManagerInfoToPage(managerInfo, page);
        return AjaxResponse.dataTables(page);
    }

    /**
     * 加载管理员信息
     *
     * @param info
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/pc/manage/loadManagerInfo")
    public String loadManagerInfo(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info, Integer id, Model model) {
        ManagerInfo managerInfo = managerService.selectManagerInfoById(id);
        if (info.getRoleId() == 3 && info.getId().intValue() != managerInfo.getParentId().intValue()) {
            return "error";
        }
        model.addAttribute("manager", managerInfo.getManagerInfo(managerInfo));
        return "/manage/manager_info";
    }

    /**
     * 更新管理员信息
     *
     * @param managerInfo
     * @return
     */
    @RequestMapping(value = "/pc/manage/UpdateManagerInfo")
    @ResponseBody
    public Object UpdateManagerInfo(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info, ManagerInfo managerInfo, HttpServletRequest request) {
        if (null == managerInfo) {
            return AjaxResponse.error("未获取到相关信息");
        }
        if (NullUtil.isNullOrEmpty(managerInfo.getId())) {
            if (NullUtil.isNullOrEmpty(managerInfo.getUserName())) {
                return AjaxResponse.error("请设置账户");
            }
            if (NullUtil.isNullOrEmpty(managerInfo.getPassword())) {
                return AjaxResponse.error("请设置密码");
            }
        } else if (NullUtil.isNotNullOrEmpty(managerInfo.getUserName())) {
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
        if (info.getRoleId() == 3) {
            managerInfo.setParentId(info.getId());
            managerInfo.setRoleId(3);
        }
        try {
            managerService.updateManagerInfo(managerInfo, info.getId(), request);
            return AjaxResponse.success(NullUtil.isNullOrEmpty(managerInfo.getId()) ? "添加成功" : "更新成功");
        } catch (Exception e) {
            return AjaxResponse.error(NullUtil.isNullOrEmpty(managerInfo.getId()) ? "添加失败" : "更新失败");
        }
    }

    @RequestMapping(value = "/pc/manage/loadSocket")
    public String loadSocket(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info, Model model) {
        model.addAttribute("sId", info.getId());
        return "/socket/socket";
    }
}
