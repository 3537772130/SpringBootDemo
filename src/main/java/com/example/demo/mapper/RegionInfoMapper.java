package com.example.demo.mapper;

import com.example.demo.entity.RegionInfo;
import com.example.demo.entity.RegionInfoExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegionInfoMapper {
    long countByExample(RegionInfoExample example);

    int deleteByPrimaryKey(Integer id);

    List<RegionInfo> selectByExample(RegionInfoExample example);

    int updateByPrimaryKeySelective(RegionInfo record);

    int updateByPrimaryKey(RegionInfo record);
}
