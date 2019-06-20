package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoExample;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.util.NullUtil;
import com.example.demo.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void addOrUpdateUserInfo(UserInfo userInfo){
        userInfo.setUpdateTime(new Date());
        if (NullUtil.isNotNullOrEmpty(userInfo.getId())){
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        } else {
            userInfo.setCreateTime(new Date());
            userInfoMapper.insertSelective(userInfo);
        }
    }
}
