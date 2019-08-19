package com.example.demo.mapper;

import com.example.demo.entity.ViewAppletInfo;
import com.example.demo.entity.ViewAppletInfoExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewAppletInfoMapper {
    long countByExample(ViewAppletInfoExample example);

    List<ViewAppletInfo> selectByExample(ViewAppletInfoExample example);
}
