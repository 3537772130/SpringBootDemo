package com.example.demo.mapper;

import com.example.demo.entity.RegionInfo;
import com.example.demo.entity.RegionInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RegionInfoMapper {
    long countByExample(RegionInfoExample example);

    int deleteByExample(RegionInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionInfo record);

    int insertSelective(RegionInfo record);

    List<RegionInfo> selectByExample(RegionInfoExample example);

    RegionInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionInfo record, @Param("example") RegionInfoExample example);

    int updateByExample(@Param("record") RegionInfo record, @Param("example") RegionInfoExample example);

    int updateByPrimaryKeySelective(RegionInfo record);

    int updateByPrimaryKey(RegionInfo record);
}
