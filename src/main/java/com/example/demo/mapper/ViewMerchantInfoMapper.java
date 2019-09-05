package com.example.demo.mapper;

import com.example.demo.entity.ViewMerchantInfo;
import com.example.demo.entity.ViewMerchantInfoExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewMerchantInfoMapper {
    long countByExample(ViewMerchantInfoExample example);

    List<ViewMerchantInfo> selectByExample(ViewMerchantInfoExample example);
}
