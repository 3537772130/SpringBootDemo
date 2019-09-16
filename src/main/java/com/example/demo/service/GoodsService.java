package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.GoodsFileMapper;
import com.example.demo.mapper.GoodsInfoMapper;
import com.example.demo.mapper.GoodsSpecsMapper;
import com.example.demo.mapper.GoodsTypeMapper;
import com.example.demo.util.NullUtil;
import com.example.demo.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 分页查询商品类型
     *
     * @param userId
     * @param name
     * @param page
     * @return
     */
    public Page selectTypeToPage(Integer userId, String name, Page page) {
        GoodsTypeExample example = new GoodsTypeExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        GoodsTypeExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(name)) {
            c.andTypeNameLike("%" + name + "%");
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
     * 分页查询商品
     *
     * @param userId
     * @param name
     * @param page
     * @return
     */
    public Page selectInfoToPage(Integer userId, String name, Page page) {
        GoodsInfoExample example = new GoodsInfoExample();
        example.setPage(page);
        example.setOrderByClause("id desc");
        GoodsInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(name)) {
            c.andGoodsNameLike("%" + name + "%");
        }
        c.andUserIdEqualTo(userId);
        long count = goodsInfoMapper.countByExample(example);
        if (count > 0) {
            page.setTotalCount(count);
            page.setDataSource(goodsInfoMapper.selectByExample(example));
        }
        return page;
    }

    /**
     * 查询商品所有文件
     *
     * @param goodsId
     * @return
     */
    public List<GoodsFile> selectFileList(Integer goodsId) {
        GoodsFileExample example = new GoodsFileExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andGoodsIdEqualTo(goodsId);
        return goodsFileMapper.selectByExample(example);
    }

    /**
     * 查询商品所有规格
     *
     * @param goodsId
     * @return
     */
    public List<GoodsSpecs> selectSpecsList(Integer goodsId) {
        GoodsSpecsExample example = new GoodsSpecsExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andGoodsIdEqualTo(goodsId);
        return goodsSpecsMapper.selectByExample(example);
    }
}
