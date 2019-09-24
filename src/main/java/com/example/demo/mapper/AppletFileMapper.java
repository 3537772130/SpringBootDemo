package com.example.demo.mapper;

import com.example.demo.entity.AppletFile;
import com.example.demo.entity.AppletFileExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppletFileMapper {
    long countByExample(AppletFileExample example);

    int deleteByExample(AppletFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppletFile record);

    int insertSelective(AppletFile record);

    List<AppletFile> selectByExample(AppletFileExample example);

    AppletFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppletFile record, @Param("example") AppletFileExample example);

    int updateByExample(@Param("record") AppletFile record, @Param("example") AppletFileExample example);

    int updateByPrimaryKeySelective(AppletFile record);

    int updateByPrimaryKey(AppletFile record);
}
