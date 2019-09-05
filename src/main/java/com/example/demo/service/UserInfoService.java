package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.CommonMapper;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.mapper.UserLoginLogMapper;
import com.example.demo.mapper.ViewMerchantInfoMapper;
import com.example.demo.util.*;
import com.example.demo.util.encryption.EncryptionUtil;
import com.example.demo.util.http.IpUtil;
import jodd.datetime.JDateTime;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ViewMerchantInfoMapper viewMerchantInfoMapper;
    @Autowired
    private CommonMapper commonMapper;

    /**
     * 查询web用户信息
     *
     * @param mobile
     * @return
     */
    public UserInfo selectUserInfoByMobile(String mobile) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andMobileEqualTo(mobile);
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
     * @param mobile
     * @param nickname
     * @param startDate
     * @param endDate
     * @param page
     * @return
     */
    public Page selectUserToPage(String mobile, String nickname, String startDate, String endDate, Boolean status, Page page) {
        UserInfoExample example = new UserInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        UserInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(mobile)) {
            c.andMobileLike(mobile + "%");
        }
        if (NullUtil.isNotNullOrEmpty(nickname)) {
            c.andNickNameLike("%" + nickname + "%");
        }
        if (NullUtil.isNotNullOrEmpty(startDate)) {
            JDateTime date = new JDateTime(startDate);
            c.andCreateDateGreaterThanOrEqualTo(date.convertToDate());
        }
        if (NullUtil.isNotNullOrEmpty(endDate)) {
            JDateTime date = new JDateTime(endDate);
            c.andCreateDateLessThanOrEqualTo(date.addDay(1).convertToDate());
        }
        if (NullUtil.isNotNullOrEmpty(status)) {
            c.andStatusEqualTo(status);
        }
        c.andIsDealerEqualTo(false);
        long count = userInfoMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(userInfoMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 查询商户列表
     *
     * @param mobile
     * @param nickname
     * @param extensionCode
     * @param startDate
     * @param endDate
     * @param page
     * @return
     */
    public Page selectMerchantToPage(String mobile, String nickname, String extensionCode, String startDate, String endDate, Boolean status, Page page) {
        ViewMerchantInfoExample example = new ViewMerchantInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        ViewMerchantInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(mobile)) {
            c.andMobileLike(mobile + "%");
        }
        if (NullUtil.isNotNullOrEmpty(nickname)) {
            c.andNickNameLike("%" + nickname + "%");
        }
        if (NullUtil.isNotNullOrEmpty(extensionCode)) {
            c.andExtensionCodeLike(extensionCode + "%");
        }
        if (NullUtil.isNotNullOrEmpty(startDate)) {
            JDateTime date = new JDateTime(startDate);
            c.andCreateDateGreaterThanOrEqualTo(date.convertToDate());
        }
        if (NullUtil.isNotNullOrEmpty(endDate)) {
            JDateTime date = new JDateTime(endDate);
            c.andCreateDateLessThanOrEqualTo(date.addDay(1).convertToDate());
        }
        if (NullUtil.isNotNullOrEmpty(status)) {
            c.andStatusEqualTo(status);
        }
        c.andIsDealerEqualTo(true);
        long count = viewMerchantInfoMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(viewMerchantInfoMapper.selectByExample(example));
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
        if (NullUtil.isNotNullOrEmpty(userInfo.getId())) {
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        } else {
            userInfo.setCreateDate(new Date());
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
        String cipher = EncryptionUtil.encryptPasswordMD5(oldPass, userInfo.getEncrypted());
        if (!cipher.equals(userInfo.getPassword())) {
            return AjaxResponse.error("原密码输入错误");
        }
        cipher = EncryptionUtil.encryptPasswordMD5(newPass, userInfo.getEncrypted());
        userInfo.setPassword(cipher);
        addOrUpdateUserInfo(userInfo.getUserInfoToPassword(userInfo));
        request.getSession().removeAttribute(Constants.VUE_USER_INFO);
        return AjaxResponse.success("修改密码成功");
    }

    /**
     * 修改用户头像
     *
     * @param id
     * @param headPortrait
     * @return
     */
    public String updateUserInfoByAvatarUrl(Integer id, String headPortrait) {
        UserInfo info = new UserInfo();
        info.setId(id);
        info.setAvatarUrl(headPortrait.replace("static\\", ""));
        userInfoMapper.updateByPrimaryKeySelective(info);
        return info.getAvatarUrl();
    }

    /**
     * 添加用户信息
     *
     * @param userInfo
     */
    public void saveUserInfo(UserInfo userInfo) {
        userInfo.setId(null);
        userInfo.setNickName(userInfo.getNickName().trim());
        userInfo.setEncrypted(RandomUtil.getRandomStr32());
        String cipher = EncryptionUtil.encryptPasswordMD5(userInfo.getPassword(), userInfo.getEncrypted());
        userInfo.setPassword(cipher);
        userInfo.setCreateDate(new Date());
        userInfoMapper.insertSelective(userInfo);
    }

    /**
     * 添加用户登录日志
     * @param id
     * @param request
     */
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
    public Page selectUserLoginLogToPage(UserLoginLog log, String startDate, String endDate, Page page) {
        UserLoginLogExample example = new UserLoginLogExample();
        example.setPage(page);
        example.setOrderByClause("login_time desc");
        UserLoginLogExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo(log.getUserId());
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
        if (NullUtil.isNotNullOrEmpty(startDate)) {
            c.andLoginTimeGreaterThanOrEqualTo(DateUtils.strToDate(startDate));
        }
        if (NullUtil.isNotNullOrEmpty(endDate)) {
            c.andLoginTimeLessThanOrEqualTo(DateUtils.strToDate(endDate));
        }
        long count = userLoginLogMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(userLoginLogMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 查询用户近期几个月活跃度情况
     *
     * @param userId
     * @param monthNumber
     * @return
     */
    public List<Map> selectUserActivity(Integer userId, Integer monthNumber) {
        String sql = "SELECT log.user_id,log.login_time,COUNT(*) AS activity FROM ( " +
                "SELECT id,user_id,DATE_FORMAT(login_time,'%Y-%m') AS login_time " +
                "FROM user_login_log " +
                "WHERE user_id = " + userId + " " +
                "ORDER BY login_time ASC " +
                ") AS log " +
                "GROUP BY log.login_time ORDER BY log.login_time DESC LIMIT " + monthNumber;
        return commonMapper.selectListMap(sql);
    }
}
