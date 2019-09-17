package com.example.demo.mapper;

import com.example.demo.entity.ViewGoodsInfo;
import com.example.demo.entity.ViewGoodsInfoExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewGoodsInfoMapper {
    long countByExample(ViewGoodsInfoExample example);

    List<ViewGoodsInfo> selectByExample(ViewGoodsInfoExample example);
}
