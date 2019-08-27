package com.example.demo.mapper;

import com.example.demo.entity.ViewAppletAuditList;
import com.example.demo.entity.ViewAppletAuditListExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewAppletAuditListMapper {
    long countByExample(ViewAppletAuditListExample example);

    List<ViewAppletAuditList> selectByExample(ViewAppletAuditListExample example);
}
