package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.util.NullUtil;
import com.example.demo.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: SpringBootDemo
 * @description: 商品服务类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-09-16 17:51
 **/
@Service
public class GoodsService {

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private GoodsFileMapper goodsFileMapper;
    @Autowired
    private GoodsSpecsMapper goodsSpecsMapper;
    @Autowired
    private ViewGoodsInfoMapper viewGoodsInfoMapper;
    @Autowired
    private ViewGoodsFileMapper viewGoodsFileMapper;
    @Autowired
    private ViewGoodsSpecsMapper viewGoodsSpecsMapper;

    /**
     * 分页查询商品类型
     *
     * @param userId
     * @param name
     * @param page
     * @return
     */
    public Page selectTypePage(Integer userId, String name, Integer status, Page page) {
        GoodsTypeExample example = new GoodsTypeExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        GoodsTypeExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(name)) {
            c.andTypeNameLike("%" + name + "%");
        }
        if (NullUtil.isNotNullOrEmpty(status)) {
            c.andTypeStatusEqualTo(status.intValue() == 1);
        }
        c.andUserIdEqualTo(userId);
        long count = goodsTypeMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(goodsTypeMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 查询商品类型信息
     *
     * @param id
     * @param userId
     * @return
     */
    public GoodsType selectGoodsType(Integer id, Integer userId) {
        GoodsTypeExample example = new GoodsTypeExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId);
        List<GoodsType> list = goodsTypeMapper.selectByExample(example);
        return NullUtil.isNotNullOrEmpty(list) ? list.get(0) : null;
    }

    /**
     * 更新商品类型信息
     *
     * @param record
     */
    public void updateGoodsType(GoodsType record) {
        if (NullUtil.isNotNullOrEmpty(record.getId())) {
            goodsTypeMapper.updateByPrimaryKeySelective(record);
        } else {
            goodsTypeMapper.insertSelective(record);
        }
    }

    /**
     * 查询正常的商品类型集合
     *
     * @param userId
     * @return
     */
    public List<GoodsType> selectTypeList(Integer userId) {
        GoodsTypeExample example = new GoodsTypeExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andUserIdEqualTo(userId).andTypeStatusEqualTo(true);
        return goodsTypeMapper.selectByExample(example);
    }

    /**
     * 分页查询商品
     *
     * @param goods
     * @param page
     * @return
     */
    public Page selectInfoPage(ViewGoodsInfo goods, Page page) {
        ViewGoodsInfoExample example = new ViewGoodsInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        ViewGoodsInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(goods.getGoodsName())) {
            c.andGoodsNameLike("%" + goods.getGoodsName() + "%");
        }
        if (NullUtil.isNotNullOrEmpty(goods.getTypeId())) {
            c.andTypeIdEqualTo(goods.getTypeId());
        }
        if (NullUtil.isNotNullOrEmpty(goods.getGoodsStatus())) {
            c.andGoodsStatusEqualTo(goods.getGoodsStatus());
        }
        c.andUserIdEqualTo(goods.getUserId());
        long count = viewGoodsInfoMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(viewGoodsInfoMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 查询商品信息
     *
     * @param id
     * @return
     */
    public GoodsInfo selectGoodsInfo(Integer id) {
        return goodsInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询商品信息
     *
     * @param id
     * @param userId
     * @return
     */
    public GoodsInfo selectGoodsInfo(Integer id, Integer userId) {
        GoodsInfoExample example = new GoodsInfoExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId);
        List<GoodsInfo> list = goodsInfoMapper.selectByExample(example);
        return NullUtil.isNotNullOrEmpty(list) ? list.get(0) : null;
    }

    /**
     * 更新商品信息
     *
     * @param record
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateGoodsInfo(GoodsInfo record) {
        record.setUpdateTime(new Date());
        if (NullUtil.isNotNullOrEmpty(record.getId())) {
            goodsInfoMapper.updateByPrimaryKeySelective(record);
        } else {
            goodsInfoMapper.insertSelective(record);
            // 设置商品文件(每个商品只允许有5个图片文件，1个视频文件)
            for (int i = 0; i < 5; i++) {
                GoodsFile file = new GoodsFile();
                file.setGoodsId(record.getId());
                file.setFileType(1);
                file.setFileStatus(false);
                goodsFileMapper.insertSelective(file);
            }
            GoodsFile file = new GoodsFile();
            file.setGoodsId(record.getId());
            file.setFileType(2);
            file.setFileStatus(false);
            goodsFileMapper.insertSelective(file);
        }
    }

    /**
     * 查询商品所有文件
     *
     * @param goodsId
     * @return
     */
    public List<ViewGoodsFile> selectFileList(Integer goodsId, Integer userId) {
        ViewGoodsFileExample example = new ViewGoodsFileExample();
        example.setOrderByClause("id asc");
        example.createCriteria().andGoodsIdEqualTo(goodsId).andUserIdEqualTo(userId);
        return viewGoodsFileMapper.selectByExample(example);
    }

    /**
     * 查询商品文件信息
     *
     * @param fileId
     * @param goodsId
     * @param userId
     * @return
     */
    public ViewGoodsFile selectFileInfo(Integer fileId, Integer goodsId, Integer userId) {
        ViewGoodsFileExample example = new ViewGoodsFileExample();
        example.createCriteria().andIdEqualTo(fileId).andGoodsIdEqualTo(goodsId).andUserIdEqualTo(userId);
        List<ViewGoodsFile> list = viewGoodsFileMapper.selectByExample(example);
        return NullUtil.isNotNullOrEmpty(list) ? list.get(0) : null;
    }

    /**
     * 修改视频文件信息
     *
     * @param id
     * @param src
     * @param status
     */
    public void updateGoodsFile(Integer id, String src, Boolean status) {
        GoodsFile file = new GoodsFile();
        file.setId(id);
        if (NullUtil.isNotNullOrEmpty(file.getFileSrc())) {
            file.setFileSrc(src.replace("static", ""));
        }
        file.setFileStatus(status);
        goodsFileMapper.updateByPrimaryKeySelective(file);
    }

    /**
     * 查询商品所有规格
     *
     * @param goodsId
     * @return
     */
    public List<ViewGoodsSpecs> selectSpecsList(Integer goodsId, Integer userId) {
        ViewGoodsSpecsExample example = new ViewGoodsSpecsExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andGoodsIdEqualTo(goodsId).andUserIdEqualTo(userId);
        return viewGoodsSpecsMapper.selectByExample(example);
    }
}
