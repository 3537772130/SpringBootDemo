package com.example.demo.mapper;

import com.example.demo.entity.ViewRoleMenuFirst;
import com.example.demo.entity.ViewRoleMenuFirstExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewRoleMenuFirstMapper {
    long countByExample(ViewRoleMenuFirstExample example);

    List<ViewRoleMenuFirst> selectByExample(ViewRoleMenuFirstExample example);
}
