package com.example.demo.mapper;

import com.example.demo.entity.AppletPage;
import com.example.demo.entity.AppletPageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppletPageMapper {
    long countByExample(AppletPageExample example);

    int deleteByExample(AppletPageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppletPage record);

    int insertSelective(AppletPage record);

    List<AppletPage> selectByExample(AppletPageExample example);

    AppletPage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppletPage record, @Param("example") AppletPageExample example);

    int updateByExample(@Param("record") AppletPage record, @Param("example") AppletPageExample example);

    int updateByPrimaryKeySelective(AppletPage record);

    int updateByPrimaryKey(AppletPage record);
}
