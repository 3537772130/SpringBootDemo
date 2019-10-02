package com.example.demo.mapper;

import com.example.demo.entity.AppletPageElement;
import com.example.demo.entity.AppletPageElementExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppletPageElementMapper {
    long countByExample(AppletPageElementExample example);

    int deleteByExample(AppletPageElementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppletPageElement record);

    int insertSelective(AppletPageElement record);

    List<AppletPageElement> selectByExample(AppletPageElementExample example);

    AppletPageElement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppletPageElement record, @Param("example") AppletPageElementExample example);

    int updateByExample(@Param("record") AppletPageElement record, @Param("example") AppletPageElementExample example);

    int updateByPrimaryKeySelective(AppletPageElement record);

    int updateByPrimaryKey(AppletPageElement record);
}
