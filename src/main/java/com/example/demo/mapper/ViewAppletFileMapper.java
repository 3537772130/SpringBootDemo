package com.example.demo.mapper;

import com.example.demo.entity.ViewAppletFile;
import com.example.demo.entity.ViewAppletFileExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewAppletFileMapper {
    long countByExample(ViewAppletFileExample example);

    List<ViewAppletFile> selectByExample(ViewAppletFileExample example);
}
