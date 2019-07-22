package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoExample;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @program: SpringBootDemo
 * @description: test  服务类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-06-14 14:52
 **/
@Service
public class UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 查询web用户信息
     * @param userName
     * @return
     */
    public UserInfo selectUserInfoByUserName(String userName){
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<UserInfo> list = userInfoMapper.selectByExample(example);
        if (NullUtil.isNotNullOrEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询用户详情
     * @param id
     * @return
     */
    public UserInfo selectUserInfoById(Integer id){
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andIdEqualTo(id);
        List<UserInfo> list = userInfoMapper.selectByExample(example);
        if (NullUtil.isNotNullOrEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询用户列表
     * @param userName
     * @param page
     * @return
     */
    public Page selectUserInfo(String userName, Page page){
        UserInfoExample example = new UserInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        UserInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(userName)){
            c.andUserNameLike(userName + "%");
        }
        long count = userInfoMapper.countByExample(example);
        if (count > 0){
            page.setTotalCount(count);
            page.setDataSource(userInfoMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 添加/修改用户信息
     * @param userInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public UserInfo addOrUpdateUserInfo(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        if (NullUtil.isNotNullOrEmpty(userInfo.getId())){
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        } else {
            userInfo.setCreateTime(new Date());
            userInfoMapper.insertSelective(userInfo);
        }
        return selectUserInfoById(userInfo.getId());
    }

    /**
     * 修改用户密码
     *
     * @param id
     * @param oldPass
     * @param newPass
     * @param request
     * @return
     */
    public Object updateUserInfoByPassword(Integer id, String oldPass, String newPass, HttpServletRequest request) {
        UserInfo userInfo = selectUserInfoById(id);
        String cipher = DesUtil.encrypt(oldPass, userInfo.getEncryptionStr());
        cipher = MD5Util.MD5(cipher);
        if (!cipher.equals(userInfo.getUserPass())) {
            return AjaxResponse.error("原密码输入错误");
        }
        cipher = DesUtil.encrypt(newPass, userInfo.getEncryptionStr());
        cipher = MD5Util.MD5(cipher);
        userInfo.setUserPass(cipher);
        addOrUpdateUserInfo(userInfo.getUserInfoToPassword(userInfo));
        request.getSession().removeAttribute(Constants.VUE_USER_INFO);
        return AjaxResponse.success("修改密码成功");
    }

    /**
     * 添加用户信息
     *
     * @param userInfo
     */
    public void saveUserInfo(UserInfo userInfo) {
        userInfo.setId(null);
        userInfo.setNickName(userInfo.getNickName().trim());
        userInfo.setEncryptionStr(RandomUtil.getRandomStr32());
        String cipher = DesUtil.encrypt(userInfo.getUserPass(), userInfo.getEncryptionStr());
        cipher = MD5Util.MD5(cipher);
        userInfo.setUserPass(cipher);
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        userInfoMapper.insertSelective(userInfo);
    }
}
