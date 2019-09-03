package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.util.NullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        example.setOrderByClause("id,menu_index asc");
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
        example.setOrderByClause("id,menu_index asc");
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
        example.setOrderByClause("id,menu_index asc");
        example.createCriteria().andRoleIdEqualTo(roleId);
        return viewRoleMenuThirdMapper.selectByExample(example);
    }


    /**
     * 检查角色菜单是否存在
     *
     * @param roleId
     * @param menuId
     * @return
     */
    public ManagerRoleMenu selectRoleMenuInfo(Integer roleId, Integer menuId) {
        ManagerRoleMenuExample example = new ManagerRoleMenuExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andMenuIdEqualTo(menuId);
        List<ManagerRoleMenu> list = managerRoleMenuMapper.selectByExample(example);
        return NullUtil.isNotNullOrEmpty(list) ? list.get(0) : null;
    }

    /**
     * 更新菜单
     *
     * @param menuInfo
     */
    public void updateMenuInfo(MenuInfo menuInfo) {
        menuInfo.setUpdateTime(new Date());
        if (NullUtil.isNotNullOrEmpty(menuInfo.getId())) {
            menuInfoMapper.updateByPrimaryKeySelective(menuInfo);
        } else {
            menuInfoMapper.insertSelective(menuInfo);
        }
    }

    /**
     * 更新角色菜单
     *
     * @param roleId
     * @param menuId
     */
    public void updateRoleMenu(Integer roleId, Integer menuId) {
        ManagerRoleMenu roleMenu = selectRoleMenuInfo(roleId, menuId);
        if (null == roleMenu) {
            roleMenu = new ManagerRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenu.setUpdateTime(new Date());
            managerRoleMenuMapper.insertSelective(roleMenu);
        } else {
            roleMenu.setStatus(!roleMenu.getStatus());
            roleMenu.setUpdateTime(new Date());
            managerRoleMenuMapper.updateByPrimaryKeySelective(roleMenu);
        }
    }
}
