package com.example.demo.service;

import com.example.demo.entity.RegionInfo;
import com.example.demo.entity.RegionInfoExample;
import com.example.demo.mapper.CommonMapper;
import com.example.demo.mapper.RegionInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: SpringBootDemo
 * @description: 地域位置服务类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-29 10:57
 **/
@Service
public class RegionService {
    @Autowired
    private RegionInfoMapper regionInfoMapper;
    @Autowired
    private CommonMapper commonMapper;

    /**
     * 查询省份信息集合
     *
     * @return
     */
    public List<Map> selectProvinceList() {
        String sql = "SELECT * FROM region_info WHERE id = parent_id";
        List<Map> list = commonMapper.selectListMap(sql);
        return list;
    }

    /**
     * 查询城市、区县信息集合
     *
     * @param parentId
     * @return
     */
    public List<RegionInfo> selectRegionList(Integer parentId) {
        RegionInfoExample example = new RegionInfoExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<RegionInfo> list = regionInfoMapper.selectByExample(example);
        return list;
    }
}
