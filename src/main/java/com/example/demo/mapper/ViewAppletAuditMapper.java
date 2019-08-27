package com.example.demo.mapper;

import com.example.demo.entity.ViewAppletAudit;
import com.example.demo.entity.ViewAppletAuditExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewAppletAuditMapper {
    long countByExample(ViewAppletAuditExample example);

    List<ViewAppletAudit> selectByExample(ViewAppletAuditExample example);
}
