package com.example.demo.mapper;

import com.example.demo.entity.ViewGoodsSpecs;
import com.example.demo.entity.ViewGoodsSpecsExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewGoodsSpecsMapper {
    long countByExample(ViewGoodsSpecsExample example);

    List<ViewGoodsSpecs> selectByExample(ViewGoodsSpecsExample example);
}
