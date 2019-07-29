package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoExample;
import com.example.demo.entity.UserLoginLog;
import com.example.demo.entity.UserLoginLogExample;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.mapper.UserLoginLogMapper;
import com.example.demo.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    /**
     * 查询web用户信息
     *
     * @param userName
     * @return
     */
    public UserInfo selectUserInfoByUserName(String userName) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<UserInfo> list = userInfoMapper.selectByExample(example);
        if (NullUtil.isNotNullOrEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询用户详情
     *
     * @param id
     * @return
     */
    public UserInfo selectUserInfoById(Integer id) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andIdEqualTo(id);
        List<UserInfo> list = userInfoMapper.selectByExample(example);
        if (NullUtil.isNotNullOrEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询用户列表
     *
     * @param userName
     * @param page
     * @return
     */
    public Page selectUserInfo(String userName, Page page) {
        UserInfoExample example = new UserInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        UserInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(userName)) {
            c.andUserNameLike(userName + "%");
        }
        long count = userInfoMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(userInfoMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 添加/修改用户信息
     *
     * @param userInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public UserInfo addOrUpdateUserInfo(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        if (NullUtil.isNotNullOrEmpty(userInfo.getId())) {
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
        userInfo.setHeadPortrait("/static/personal/default-avatar.jpeg");
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        userInfoMapper.insertSelective(userInfo);
    }

    @Async("taskExecutor")
    public void saveUserLoginLog(Integer id, HttpServletRequest request) {
        UserLoginLog record = new UserLoginLog();
        record.setUserId(id);
        String ip = IpUtil.getForIp(request);
        record.setIpAddress(ip);
        record.setLoginTime(new Date());
        String json_result = null;
        json_result = IpUtil.getAddresses("ip=" + ip, "UTF-8");
        if (NullUtil.isNotNullOrEmpty(json_result)) {
            JSONObject json = JSONObject.fromObject(json_result);
            System.out.println("json数据： " + json);
            JSONObject obj = JSONObject.fromObject(json.get("data"));
            record.setCountry(obj.get("country").toString());
            record.setRegion(obj.get("region").toString());
            record.setCity(obj.get("city").toString());
            record.setCounty(obj.get("county").toString());
            record.setArea(obj.get("area").toString());
            record.setIsp(obj.get("isp").toString());
        }
        userLoginLogMapper.insertSelective(record);
    }

    /**
     * 分页-查询用户登录记录
     *
     * @param log
     * @param page
     * @return
     */
    public Page selectUserLoginLogToPage(UserLoginLog log, Page page) {
        UserLoginLogExample example = new UserLoginLogExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        UserLoginLogExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo(log.getUserId());
//        if (NullUtil.isNotNullOrEmpty(log.getCountry())){
//            c.andCountryEqualTo(log.getCountry());
//        }
        if (NullUtil.isNotNullOrEmpty(log.getRegion())) {
            c.andRegionEqualTo(log.getRegion());
        }
        if (NullUtil.isNotNullOrEmpty(log.getCity())) {
            c.andCityEqualTo(log.getCity());
        }
        if (NullUtil.isNotNullOrEmpty(log.getCounty())) {
            c.andCountyEqualTo(log.getCounty());
        }
        if (NullUtil.isNotNullOrEmpty(log.getArea())) {
            c.andAreaLike("%" + log.getArea() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(log.getIpAddress())) {
            c.andIpAddressLike("%" + log.getIpAddress() + "%");
        }
        long count = userLoginLogMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(userLoginLogMapper.selectByExample(example));
        }
        return page;
    }

}
