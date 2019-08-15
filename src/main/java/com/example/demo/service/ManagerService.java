package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.util.NullUtil;
import com.example.demo.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: demo
 * @description: 后台管理员服务类
 * @author: zhouhuahu
 * @create: 2019-08-16 00:45
 **/
@Service
public class ManagerService {

    @Autowired
    private ManagerInfoMapper managerInfoMapper;
    @Autowired
    private ViewManagerInfoMapper viewManagerInfoMapper;
    @Autowired
    private RoleInfoMapper roleInfoMapper;
    @Autowired
    private ManagerLogMapper managerLogMapper;
    @Autowired
    private ViewManagerLogMapper viewManagerLogMapper;

    /**
     * 查询管理员信息
     * @param id
     * @return
     */
    public ManagerInfo selectManagerInfoById(Integer id){
        return managerInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询管理员信息
     * @param userName
     * @return
     */
    public ManagerInfo selectManagerInfoByUserName(String userName){
        ManagerInfoExample example = new ManagerInfoExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<ManagerInfo> list = managerInfoMapper.selectByExample(example);
        if (NullUtil.isNotNullOrEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 分页查询管理员信息集合
     * @param info
     * @param page
     * @return
     */
    public Page selectManagerInfoByPage(ViewManagerInfo info, Page page){
        ViewManagerInfoExample example = new ViewManagerInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        ViewManagerInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(info.getUserName())){
            c.andUserNameEqualTo(info.getUserName());
        }
        if (NullUtil.isNotNullOrEmpty(info.getRoleId())){
            c.andRoleIdEqualTo(info.getRoleId());
        }
        if (NullUtil.isNotNullOrEmpty(info.getMobile())){
            c.andMobileLike(info.getMobile() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getQqAccount())){
            c.andQqAccountLike(info.getQqAccount() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getWeChatAccount())){
            c.andWeChatAccountLike(info.getWeChatAccount() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getParentUserName())){
            c.andParentUserNameEqualTo(info.getParentUserName());
        }
        if (NullUtil.isNotNullOrEmpty(info.getParentNickName())){
            c.andParentNickNameLike("%" + info.getParentNickName() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getStatus())){
            c.andStatusEqualTo(info.getStatus());
        }
        c.andIdNotEqualTo(1);
        long count = viewManagerInfoMapper.countByExample(example);
        if (count > 0){
            page.setTotalCount(count);
            page.setDataSource(viewManagerInfoMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 更新管理员信息
     * @param info
     */
    public void updateManagerInfo(ManagerInfo info){
        if (NullUtil.isNotNullOrEmpty(info.getId())){
            managerInfoMapper.updateByPrimaryKeySelective(info);
        } else {
            info.setCreateDate(new Date());
            managerInfoMapper.insertSelective(info);
        }
    }

    /**
     * 查询管理员角色信息
     * @param id
     * @return
     */
    public RoleInfo selectRoleInfoById(Integer id){
        return roleInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询管理员角色信息集合
     * @param info
     * @param page
     * @return
     */
    public Page selectRoleInfoByRoleName(RoleInfo info, Page page){
        RoleInfoExample example = new RoleInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        RoleInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(info.getRoleName())){
            c.andRoleNameLike("%" + info.getRoleName() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getStatus())){
            c.andStatusEqualTo(info.getStatus());
        }
        long count = roleInfoMapper.countByExample(example);
        if (count > 0){
            page.setTotalCount(count);
            page.setDataSource(roleInfoMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 更新管理员角色信息
     * @param info
     */
    public void updateRoleInfo(RoleInfo info){
        if (NullUtil.isNotNullOrEmpty(info.getId())){
            roleInfoMapper.updateByPrimaryKeySelective(info);
        } else {
            info.setCreateDate(new Date());
            roleInfoMapper.insertSelective(info);
        }
    }
}
