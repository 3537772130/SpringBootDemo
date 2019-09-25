package com.example.demo.mapper;

import com.example.demo.entity.AppletPageElementContent;
import com.example.demo.entity.AppletPageElementContentExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppletPageElementContentMapper {
    long countByExample(AppletPageElementContentExample example);

    int deleteByExample(AppletPageElementContentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppletPageElementContent record);

    int insertSelective(AppletPageElementContent record);

    List<AppletPageElementContent> selectByExample(AppletPageElementContentExample example);

    AppletPageElementContent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppletPageElementContent record, @Param("example") AppletPageElementContentExample example);

    int updateByExample(@Param("record") AppletPageElementContent record, @Param("example") AppletPageElementContentExample example);

    int updateByPrimaryKeySelective(AppletPageElementContent record);

    int updateByPrimaryKey(AppletPageElementContent record);
}
