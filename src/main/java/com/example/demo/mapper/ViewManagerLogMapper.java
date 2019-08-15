package com.example.demo.mapper;

import com.example.demo.entity.ViewManagerLog;
import com.example.demo.entity.ViewManagerLogExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewManagerLogMapper {
    long countByExample(ViewManagerLogExample example);

    List<ViewManagerLog> selectByExample(ViewManagerLogExample example);
}
