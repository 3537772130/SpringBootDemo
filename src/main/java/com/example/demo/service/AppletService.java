package com.example.demo.service;

import com.example.demo.entity.AppletInfo;
import com.example.demo.entity.AppletInfoExample;
import com.example.demo.entity.ViewAppletInfo;
import com.example.demo.entity.ViewAppletInfoExample;
import com.example.demo.mapper.AppletInfoMapper;
import com.example.demo.mapper.ViewAppletInfoMapper;
import com.example.demo.util.NullUtil;
import com.example.demo.util.Page;
import com.example.demo.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: SpringBootDemo
 * @description: 小程序信息服务类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-08-19 09:38
 **/
@Service
public class AppletService {
    @Autowired
    private AppletInfoMapper appletInfoMapper;
    @Autowired
    private ViewAppletInfoMapper viewAppletInfoMapper;

    /**
     * 查询小程序信息
     *
     * @param id
     * @return
     */
    public AppletInfo selectAppletInfoById(Integer id) {
        return appletInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询小程序信息
     *
     * @param id
     * @param userId
     * @return
     */
    public ViewAppletInfo selectAppletInfo(Integer id, Integer userId) {
        ViewAppletInfoExample example = new ViewAppletInfoExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId);
        List<ViewAppletInfo> list = viewAppletInfoMapper.selectByExample(example);
        return NullUtil.isNotNullOrEmpty(list) ? list.get(0) : null;
    }

    /**
     * 查询小程序信息
     *
     * @param appletCode
     * @return
     */
    public AppletInfo selectAppletInfo(String appletCode) {
        AppletInfoExample example = new AppletInfoExample();
        example.createCriteria().andAppletCodeEqualTo(appletCode);
        List<AppletInfo> list = appletInfoMapper.selectByExample(example);
        return NullUtil.isNotNullOrEmpty(list) ? list.get(0) : null;
    }

    /**
     * 用户分页查询小程序列表
     *
     * @param info
     * @param page
     * @return
     */
    public Page selectAppletInfoToPage(AppletInfo info, Page page) {
        AppletInfoExample example = new AppletInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        AppletInfoExample.Criteria c = example.createCriteria().andUserIdEqualTo(info.getUserId());
        if (NullUtil.isNotNullOrEmpty(info.getAppletCode())) {
            c.andAppletCodeLike("%" + info.getAppletCode() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getAppletName())) {
            c.andAppletNameLike("%" + info.getAppletName() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getIfSelling())) {
            c.andIfSellingEqualTo(info.getIfSelling());
        }
        long count = appletInfoMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(appletInfoMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 管理员分页查询小程序列表
     *
     * @param info
     * @param page
     * @return
     */
    public Page selectAppletInfoToPage(ViewAppletInfo info, Page page) {
        ViewAppletInfoExample example = new ViewAppletInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        ViewAppletInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(info.getAppletCode())) {
            c.andAppletCodeLike("%" + info.getAppletCode() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getAppletName())) {
            c.andAppletNameLike("%" + info.getAppletName() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getMobile())) {
            c.andMobileLike(info.getMobile() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getProvince())) {
            c.andProvinceEqualTo(info.getProvince());
            if (NullUtil.isNotNullOrEmpty(info.getCity())) {
                c.andCityEqualTo(info.getCity());
                if (NullUtil.isNotNullOrEmpty(info.getCounty())) {
                    c.andCountyEqualTo(info.getCounty());
                }
            }
        }
        if (NullUtil.isNotNullOrEmpty(info.getRecommenderAccount())) {
            c.andRecommenderAccountLike(info.getRecommenderAccount() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(info.getIfRetail())) {
            c.andIfRetailEqualTo(info.getIfRetail());
        }
        if (NullUtil.isNotNullOrEmpty(info.getIfSelling())) {
            c.andIfSellingEqualTo(info.getIfSelling());
        }
        if (NullUtil.isNotNullOrEmpty(info.getStatus())) {
            c.andStatusEqualTo(info.getStatus());
        }
        long count = viewAppletInfoMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(viewAppletInfoMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 更新小程序信息
     *
     * @param info
     */
    public void saveAppletInfo(AppletInfo info) {
        info.setUpdateTime(new Date());
        info.setAppletLogo(null);
        info.setLicenseSrc(null);
        info.setAppletCode("AC" + RandomUtil.getTimeStamp());
        info.setIfSelling(false);
        info.setStatus(0);
        appletInfoMapper.insertSelective(info);
    }

    /**
     * 更新小程序图片
     *
     * @param id
     * @param appletLogo
     * @param licenseSrc
     */
    public void updateAppletInfo(Integer id, String appletLogo, String licenseSrc) {
        AppletInfo info = new AppletInfo();
        info.setId(id);
        info.setAppletLogo(appletLogo);
        info.setLicenseSrc(licenseSrc);
        updateAppletInfo(info);
    }

    /**
     * 更新小程序信息
     *
     * @param info
     */
    public void updateAppletInfo(AppletInfo info) {
        appletInfoMapper.updateByPrimaryKeySelective(info);
    }
}
