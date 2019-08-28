package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.util.FileUtil;
import com.example.demo.util.NullUtil;
import com.example.demo.util.Page;
import com.example.demo.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private AppletAuditMapper appletAuditMapper;
    @Autowired
    private ViewAppletAuditMapper viewAppletAuditMapper;
    @Autowired
    private ViewAppletAuditListMapper viewAppletAuditListMapper;

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
     * @return
     */
    public ViewAppletInfo selectAppletInfo(Integer id) {
        return selectAppletInfo(id, null);
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
        ViewAppletInfoExample.Criteria c = example.createCriteria();
        c.andIdEqualTo(id);
        if (NullUtil.isNotNullOrEmpty(userId)) {
            c.andUserIdEqualTo(userId);
        }
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
            c.andRecommenderAccountLike(info.getRecommenderAccount());
        }
        if (NullUtil.isNotNullOrEmpty(info.getIfRetail())) {
            c.andIfRetailEqualTo(info.getIfRetail());
        }
        if (NullUtil.isNotNullOrEmpty(info.getIfSelling())) {
            c.andIfSellingEqualTo(info.getIfSelling());
        }
        c.andStatusEqualTo(1);
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
    @Transactional(rollbackFor = Exception.class)
    public void saveAppletInfo(AppletInfo info) {
        info.setUpdateTime(new Date());
        info.setAppletLogo(null);
        info.setLicenseSrc(null);
        info.setAppletCode("AC" + RandomUtil.getTimeStamp());
        info.setIfSelling(false);
        info.setStatus(0);
        appletInfoMapper.insertSelective(info);
        // 更新图片
        updateAppletPic(info);
    }

    /**
     * 更新小程序图片
     *
     * @param info
     */
    public void updateAppletPic(AppletInfo info) {
        String appletLogo = FileUtil.copyFile("static\\images\\applet-logo\\draft\\", "U" + info.getUserId() + "-APPLET-LOGO.jpg",
                "static\\images\\applet-logo\\", info.getAppletCode() + ".jpg");
        String licenseSrc = FileUtil.copyFile("static\\images\\applet-license\\draft\\", "U" + info.getUserId() + "-APPLET-LICENSE.jpg",
                "static\\images\\applet-license\\", info.getAppletCode() + ".jpg");
        AppletInfo appletInfo = new AppletInfo();
        appletInfo.setId(info.getId());
        appletInfo.setAppletLogo(appletLogo);
        appletInfo.setLicenseSrc(licenseSrc);
        updateAppletInfo(info);
    }

    /**
     * 更新小程序信息
     *
     * @param info
     */
    public void updateAppletInfo(AppletInfo info) {
        info.setStatus(0);
        appletInfoMapper.updateByPrimaryKeySelective(info);

        AppletAudit audit = new AppletAudit();
        audit.setAppletId(info.getId());
        audit.setAppletCode(info.getAppletCode());
        audit.setResult(0);
        audit.setRemark("用户提交信息");
        appletAuditMapper.insertSelective(audit);
    }


    /**
     * 查询小程序审核记录
     *
     * @param appletId
     * @return
     */
    public List<ViewAppletAudit> selectAppletAuditList(Integer appletId) {
        ViewAppletAuditExample example = new ViewAppletAuditExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andAppletIdEqualTo(appletId);
        return viewAppletAuditMapper.selectByExample(example);
    }

    /**
     * 查询小程序最新审核记录
     *
     * @param appletId
     * @return
     */
    public ViewAppletAudit selectAppletNewAudit(Integer appletId) {
        Page page = new Page(0, 1);
        ViewAppletAuditExample example = new ViewAppletAuditExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        example.createCriteria().andAppletIdEqualTo(appletId);
        List<ViewAppletAudit> list = viewAppletAuditMapper.selectByExample(example);
        return NullUtil.isNotNullOrEmpty(list) ? list.get(0) : null;
    }

    /**
     * 查询小程序审核列表
     *
     * @param record
     * @param page
     * @return
     */
    public Page selectAppletAuditToPage(ViewAppletAuditList record, Page page) {
        ViewAppletAuditListExample example = new ViewAppletAuditListExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        ViewAppletAuditListExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(record.getAppletCode())) {
            c.andAppletCodeLike("%" + record.getAppletCode() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(record.getAppletName())) {
            c.andAppletNameLike("%" + record.getAppletName() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(record.getMobile())) {
            c.andMobileLike(record.getMobile() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(record.getProvince())) {
            c.andProvinceEqualTo(record.getProvince());
            if (NullUtil.isNotNullOrEmpty(record.getCity())) {
                c.andCityEqualTo(record.getCity());
                if (NullUtil.isNotNullOrEmpty(record.getCounty())) {
                    c.andCountyEqualTo(record.getCounty());
                }
            }
        }
        if (NullUtil.isNotNullOrEmpty(record.getRecommenderAccount())) {
            c.andRecommenderAccountLike(record.getRecommenderAccount() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(record.getIfRetail())) {
            c.andIfRetailEqualTo(record.getIfRetail());
        }
        if (NullUtil.isNotNullOrEmpty(record.getIfSelling())) {
            c.andIfSellingEqualTo(record.getIfSelling());
        }
        if (NullUtil.isNotNullOrEmpty(record.getStatus())) {
            c.andStatusEqualTo(record.getStatus());
        }
        if (NullUtil.isNotNullOrEmpty(record.getAuditorUserName())) {
            c.andAuditorUserNameLike(record.getAuditorUserName());
        }
        if (NullUtil.isNotNullOrEmpty(record.getAuditResult())) {
            c.andAuditResultEqualTo(record.getAuditResult());
        }
        if (NullUtil.isNotNullOrEmpty(record.getStatus())) {
            c.andStatusEqualTo(record.getStatus());
        }
        long count = viewAppletAuditListMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(viewAppletAuditListMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 查询小程序审核信息
     *
     * @param id
     * @return
     */
    public ViewAppletAuditList selectAppletAuditDetails(Integer id) {
        ViewAppletAuditListExample example = new ViewAppletAuditListExample();
        example.createCriteria().andIdEqualTo(id);
        List<ViewAppletAuditList> list = viewAppletAuditListMapper.selectByExample(example);
        return NullUtil.isNotNullOrEmpty(list) ? list.get(0) : null;
    }

    /**
     * 添加小程序审核信息
     *
     * @param appletAudit
     */
    @Transactional(rollbackFor = Exception.class)
    public void addAppletAudit(AppletAudit appletAudit) {
        appletAudit.setAuditTime(new Date());
        appletAuditMapper.insertSelective(appletAudit);

        if (appletAudit.getResult().intValue() != 1) {
            AppletInfo applet = new AppletInfo();
            applet.setId(appletAudit.getAppletId());
            applet.setStatus(appletAudit.getResult().intValue() == 2 ? 1 : -1);
            appletInfoMapper.updateByPrimaryKeySelective(applet);
        }
    }
}
