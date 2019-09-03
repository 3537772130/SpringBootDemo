package com.example.demo.mapper;

import com.example.demo.entity.ViewRoleMenuThird;
import com.example.demo.entity.ViewRoleMenuThirdExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewRoleMenuThirdMapper {
    long countByExample(ViewRoleMenuThirdExample example);

    List<ViewRoleMenuThird> selectByExample(ViewRoleMenuThirdExample example);
}
