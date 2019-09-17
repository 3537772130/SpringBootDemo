package com.example.demo.mapper;

import com.example.demo.entity.ViewGoodsFile;
import com.example.demo.entity.ViewGoodsFileExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewGoodsFileMapper {
    long countByExample(ViewGoodsFileExample example);

    List<ViewGoodsFile> selectByExample(ViewGoodsFileExample example);
}
