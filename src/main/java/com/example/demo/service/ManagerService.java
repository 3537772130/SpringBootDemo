package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.util.NullUtil;
import com.example.demo.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private ManagerRoleMapper managerRoleMapper;
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
    public ViewManagerInfo selectManagerInfoByUserName(String userName) {
        ViewManagerInfoExample example = new ViewManagerInfoExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<ViewManagerInfo> list = viewManagerInfoMapper.selectByExample(example);
        if (NullUtil.isNotNullOrEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询管理员信息
     *
     * @param id
     * @return
     */
    public ViewManagerInfo selectViewManagerInfoById(Integer id) {
        ViewManagerInfoExample example = new ViewManagerInfoExample();
        example.createCriteria().andIdEqualTo(id);
        List<ViewManagerInfo> list = viewManagerInfoMapper.selectByExample(example);
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
    public Page selectManagerInfoToPage(ViewManagerInfo info, Page page) {
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
        if (NullUtil.isNotNullOrEmpty(info.getProvince())) {
            c.andProvinceEqualTo(info.getProvince());
        }
        if (NullUtil.isNotNullOrEmpty(info.getCity())) {
            c.andCityEqualTo(info.getCity());
        }
        if (NullUtil.isNotNullOrEmpty(info.getCounty())) {
            c.andCountyEqualTo(info.getCounty());
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
    public ManagerRole selectManagerRoleById(Integer id) {
        return managerRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询角色Map集合
     *
     * @return
     */
    public List<Map> selectRoleToMap() {
        ManagerRoleExample example = new ManagerRoleExample();
        example.setOrderByClause("id asc");
        List<ManagerRole> list = managerRoleMapper.selectByExample(example);
        if (NullUtil.isNotNullOrEmpty(list)) {
            List<Map> mapList = new ArrayList<>();
            for (ManagerRole role : list) {
                Map map = new HashMap();
                map.put("id", role.getId());
                map.put("name", role.getRoleName());
                mapList.add(map);
            }
            return mapList;
        }
        return null;
    }

    /**
     * 分页查询管理员角色信息集合
     * @param info
     * @param page
     * @return
     */
    public Page selectRoleToPage(ManagerRole info, Page page) {
        ManagerRoleExample example = new ManagerRoleExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        ManagerRoleExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(info.getRoleName())){
            c.andRoleNameLike("%" + info.getRoleName() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getStatus())){
            c.andStatusEqualTo(info.getStatus());
        }
        long count = managerRoleMapper.countByExample(example);
        if (count > 0){
            page.setTotalCount(count);
            page.setDataSource(managerRoleMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 更新管理员角色信息
     * @param info
     */
    public void updateManagerRole(ManagerRole info) {
        info.setUpdateDate(new Date());
        if (NullUtil.isNotNullOrEmpty(info.getId())) {
            managerRoleMapper.updateByPrimaryKeySelective(info);
        } else {
            managerRoleMapper.insertSelective(info);
        }
    }
}
