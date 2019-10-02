package com.example.demo.mapper;

import com.example.demo.entity.ViewAppletPageElementDefault;
import com.example.demo.entity.ViewAppletPageElementDefaultExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewAppletPageElementDefaultMapper {
    long countByExample(ViewAppletPageElementDefaultExample example);

    List<ViewAppletPageElementDefault> selectByExample(ViewAppletPageElementDefaultExample example);
}
