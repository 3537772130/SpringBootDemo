package com.example.demo.mapper;

import com.example.demo.entity.ViewRoleMenuSecond;
import com.example.demo.entity.ViewRoleMenuSecondExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewRoleMenuSecondMapper {
    long countByExample(ViewRoleMenuSecondExample example);

    List<ViewRoleMenuSecond> selectByExample(ViewRoleMenuSecondExample example);
}
