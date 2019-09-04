package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.util.NullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: SpringBootDemo
 * @description: 菜单服务类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-09-02 10:15
 **/
@Service
public class MenuService {
    @Autowired
    private MenuInfoMapper menuInfoMapper;
    @Autowired
    private ManagerRoleMenuMapper managerRoleMenuMapper;
    @Autowired
    private ViewRoleMenuFirstMapper viewRoleMenuFirstMapper;
    @Autowired
    private ViewRoleMenuSecondMapper viewRoleMenuSecondMapper;
    @Autowired
    private ViewRoleMenuThirdMapper viewRoleMenuThirdMapper;

    /**
     * 查询菜单信息
     *
     * @param id
     * @return
     */
    public MenuInfo selectMenuInfo(Integer id) {
        return menuInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询菜单集合
     *
     * @param level
     * @return
     */
    public List<MenuInfo> selectMenuList(Integer level, Boolean status) {
        MenuInfoExample example = new MenuInfoExample();
        example.setOrderByClause("menu_index asc");
        MenuInfoExample.Criteria c = example.createCriteria().andMenuLevelEqualTo(level);
        if (NullUtil.isNotNullOrEmpty(status)) {
            c.andStatusEqualTo(status);
        }
        return menuInfoMapper.selectByExample(example);
    }

    /**
     * 查询一级菜单集合
     *
     * @return
     */
    public List<MenuInfo> selectMenuListByFirst() {
        return selectMenuList(1, null);
    }

    /**
     * 查询二级菜单集合
     *
     * @return
     */
    public List<MenuInfo> selectMenuListBySecond() {
        return selectMenuList(2, null);
    }

    /**
     * 查询三级菜单集合
     *
     * @return
     */
    public List<MenuInfo> selectMenuListByThird() {
        return selectMenuList(3, null);
    }

    /**
     * 查询角色一级菜单集合
     *
     * @param roleId
     * @return
     */
    public List<ViewRoleMenuFirst> selectMenuListByFirst(Integer roleId) {
        ViewRoleMenuFirstExample example = new ViewRoleMenuFirstExample();
        example.setOrderByClause("menu_index asc");
        example.createCriteria().andRoleIdEqualTo(roleId);
        return viewRoleMenuFirstMapper.selectByExample(example);
    }

    /**
     * 查询角色二级菜单集合
     *
     * @param roleId
     * @return
     */
    public List<ViewRoleMenuSecond> selectMenuListBySecond(Integer roleId) {
        ViewRoleMenuSecondExample example = new ViewRoleMenuSecondExample();
        example.setOrderByClause("menu_index asc");
        example.createCriteria().andRoleIdEqualTo(roleId);
        return viewRoleMenuSecondMapper.selectByExample(example);
    }

    /**
     * 查询角色三级菜单集合
     *
     * @param roleId
     * @return
     */
    public List<ViewRoleMenuThird> selectMenuListByThird(Integer roleId) {
        ViewRoleMenuThirdExample example = new ViewRoleMenuThirdExample();
        example.setOrderByClause("menu_index asc");
        example.createCriteria().andRoleIdEqualTo(roleId);
        return viewRoleMenuThirdMapper.selectByExample(example);
    }


    /**
     * 根据标识查询授权菜单
     *
     * @param logo
     * @return
     */
    public List<ViewRoleMenuThird> selectRoleMenuInfo(String logo) {
        ViewRoleMenuThirdExample example = new ViewRoleMenuThirdExample();
        example.createCriteria().andMenuLogoEqualTo(logo);
        return viewRoleMenuThirdMapper.selectByExample(example);
    }

    /**
     * 更新菜单
     *
     * @param menuInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMenuInfo(MenuInfo menuInfo) {
        menuInfo.setUpdateTime(new Date());
        if (NullUtil.isNotNullOrEmpty(menuInfo.getId())) {
            menuInfoMapper.updateByPrimaryKeySelective(menuInfo);
        } else {
            menuInfoMapper.insertSelective(menuInfo);
            if (menuInfo.getMenuLevel().intValue() == 3) {
                ManagerRoleMenu record = new ManagerRoleMenu();
                record.setRoleId(1);
                record.setMenuId(menuInfo.getId());
                record.setUpdateTime(new Date());
                record.setStatus(true);
                managerRoleMenuMapper.insertSelective(record);
            }
        }
    }

    /**
     * 查询角色拥有权限的菜单ID集合
     *
     * @param roleId
     * @return
     */
    public List<Integer> selectRoleMenuIdToList(Integer roleId) {
        ManagerRoleMenuExample example = new ManagerRoleMenuExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andStatusEqualTo(true);
        List<ManagerRoleMenu> list = managerRoleMenuMapper.selectByExample(example);
        List<Integer> list1 = new ArrayList<>();
        for (ManagerRoleMenu record : list) {
            list1.add(record.getMenuId());
        }
        return list1;
    }

    /**
     * 更新角色菜单
     *
     * @param roleId
     * @param idList
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleMenu(Integer roleId, List<Integer> idList) {
        // 刷选未选择过的权限，添加到角色菜单权限表
        ManagerRoleMenuExample example = new ManagerRoleMenuExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<ManagerRoleMenu> list = managerRoleMenuMapper.selectByExample(example);
        List<Integer> idList1 = new ArrayList<>();
        for (Integer menuId : idList) {
            boolean bool = true;
            for (ManagerRoleMenu record : list) {
                if (menuId.intValue() == record.getMenuId().intValue()) {
                    bool = false;
                    break;
                }
            }
            if (bool) {
                idList1.add(menuId);
            }
        }
        for (Integer menuId : idList1) {
            ManagerRoleMenu roleMenu = new ManagerRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenu.setUpdateTime(new Date());
            roleMenu.setStatus(true);
            managerRoleMenuMapper.insertSelective(roleMenu);
        }
        // 已选择的菜单全部开启权限
        example = new ManagerRoleMenuExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andMenuIdIn(idList);
        ManagerRoleMenu record = new ManagerRoleMenu();
        record.setStatus(true);
        managerRoleMenuMapper.updateByExampleSelective(record, example);
        // 未选择的菜单全部关闭权限
        example = new ManagerRoleMenuExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andMenuIdNotIn(idList);
        record = new ManagerRoleMenu();
        record.setStatus(false);
        managerRoleMenuMapper.updateByExampleSelective(record, example);

    }
}
