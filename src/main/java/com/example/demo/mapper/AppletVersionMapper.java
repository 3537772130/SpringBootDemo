package com.example.demo.mapper;

import com.example.demo.entity.AppletVersion;
import com.example.demo.entity.AppletVersionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppletVersionMapper {
    long countByExample(AppletVersionExample example);

    int deleteByExample(AppletVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppletVersion record);

    int insertSelective(AppletVersion record);

    List<AppletVersion> selectByExample(AppletVersionExample example);

    AppletVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppletVersion record, @Param("example") AppletVersionExample example);

    int updateByExample(@Param("record") AppletVersion record, @Param("example") AppletVersionExample example);

    int updateByPrimaryKeySelective(AppletVersion record);

    int updateByPrimaryKey(AppletVersion record);
}
