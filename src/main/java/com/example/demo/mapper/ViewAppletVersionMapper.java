package com.example.demo.mapper;

import com.example.demo.entity.ViewAppletVersion;
import com.example.demo.entity.ViewAppletVersionExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewAppletVersionMapper {
    long countByExample(ViewAppletVersionExample example);

    List<ViewAppletVersion> selectByExample(ViewAppletVersionExample example);
}
