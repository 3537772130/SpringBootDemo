package com.example.demo.mapper;

import com.example.demo.entity.ViewManagerInfo;
import com.example.demo.entity.ViewManagerInfoExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewManagerInfoMapper {
    long countByExample(ViewManagerInfoExample example);

    List<ViewManagerInfo> selectByExample(ViewManagerInfoExample example);
}