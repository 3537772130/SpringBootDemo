package com.example.demo.controller.vue;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: SpringBootDemo
 * @description: vue用户信息控制层
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-03 16:03
 **/
@RestController
@RequestMapping(value = "/api/user/")
public class VueUserInfoController {
    private static final Logger log = LoggerFactory.getLogger(VueUserInfoController.class);
    @Autowired
    public UserInfoService userInfoService;

    /**
     * 获取登录信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "getUserInfo")
    public Object getUserInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user) {
        if (null == user) {
            return AjaxResponse.error("请先登录");
        }
        return AjaxResponse.success(user);
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @param info
     * @return
     */
    @RequestMapping(value = "updateUserInfo")
    public Object updateUserInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, UserInfo info, HttpServletRequest request) {
        try {
            if (null == info) {
                return AjaxResponse.error("提交信息失败");
            }
            if (NullUtil.isNullOrEmpty(info.getNickName())) {
                return AjaxResponse.error("用户昵称不能为空");
            }
            if (!RegularUtil.checkName(info.getNickName().trim())) {
                return AjaxResponse.error("昵称不能含有特殊字符");
            }
            info.setId(user.getId());
            info = userInfoService.addOrUpdateUserInfo(info.getUserInfo(info));
            request.getSession().setAttribute(Constants.VUE_USER_INFO, SerializeUtil.serialize(info.getUserInfo(info)));
            return AjaxResponse.success(info.getUserInfo(info));
        } catch (Exception e) {
            log.error("修改用户信息出错{}", e);
            return AjaxResponse.error("修改信息失败");
        }
    }

    /**
     * 修改用户密码(登录状态下)
     *
     * @param user
     * @param oldPass
     * @param newPass
     * @return
     */
    @RequestMapping(value = "updateUserInfoByPassword")
    public Object updateUserInfoByPassword(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, String oldPass, String newPass) {
        try {
            if (NullUtil.isNullOrEmpty(oldPass)) {
                return AjaxResponse.error("请输入原密码");
            }
            if (oldPass.length() < 6 || oldPass.length() > 20) {
                return AjaxResponse.error("原密码长度不符");
            }
            if (NullUtil.isNullOrEmpty(newPass)) {
                return AjaxResponse.error("请输入新密码");
            }
            if (newPass.length() < 6 || newPass.length() > 20) {
                return AjaxResponse.error("新密码长度不符");
            }
            UserInfo userInfo = userInfoService.selectUserInfoById(user.getId());
            String cipher = DesUtil.encrypt(oldPass, userInfo.getEncryptionStr());
            cipher = MD5Util.MD5(cipher);
            if (!cipher.equals(userInfo.getUserPass())) {
                return AjaxResponse.error("原密码输入错误");
            }
            cipher = DesUtil.encrypt(newPass, userInfo.getEncryptionStr());
            cipher = MD5Util.MD5(cipher);
            userInfo.setUserPass(cipher);
            userInfoService.addOrUpdateUserInfo(userInfo.getUserInfoToPassword(userInfo));
            return AjaxResponse.success("修改密码成功");
        } catch (Exception e) {
            log.error("修改密码出错{}", e);
            return AjaxResponse.error("修改密码失败");
        }
    }
}
