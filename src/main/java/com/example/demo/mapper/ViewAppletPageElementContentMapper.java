package com.example.demo.mapper;

import com.example.demo.entity.ViewAppletPageElementContent;
import com.example.demo.entity.ViewAppletPageElementContentExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewAppletPageElementContentMapper {
    long countByExample(ViewAppletPageElementContentExample example);

    List<ViewAppletPageElementContent> selectByExample(ViewAppletPageElementContentExample example);
}
