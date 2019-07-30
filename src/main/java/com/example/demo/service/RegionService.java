package com.example.demo.service;

import com.example.demo.entity.RegionInfo;
import com.example.demo.entity.RegionInfoExample;
import com.example.demo.mapper.RegionInfoMapper;
import com.example.demo.util.Constants;
import com.example.demo.util.NullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: SpringBootDemo
 * @description: 地域位置服务类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-29 10:57
 **/
@Service
@Component
public class RegionService implements ApplicationRunner {
    @Autowired
    private RegionInfoMapper regionInfoMapper;

    private final Integer PROVINCE_LEVEL = 1;
    private final Integer CITY_LEVEL = 2;
    private final Integer COUNTY_LEVEL = 3;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<RegionInfo> provinceList = selectProvinceList();
        List<RegionInfo> cityList = selectCityList();
        List<RegionInfo> countyList = selectCountyList();

        List<Map> list2 = new ArrayList<>();
        List<Map> list22 = new ArrayList<>();

        for (int i = 0; i < cityList.size(); i++) {
            RegionInfo record2 = cityList.get(i);

            Map map2 = new HashMap();
            map2.put("parentId", record2.getParentId());
            map2.put("value", record2.getId());
            map2.put("label", record2.getName());
            List<Map> list3 = new ArrayList<>();
            Map map22 = new HashMap();
            map22.put("parentId", record2.getParentId());
            map22.put("value", record2.getName());
            map22.put("label", record2.getName());
            List<Map> list33 = new ArrayList<>();

            for (int k = 0; k < countyList.size(); k++) {
                RegionInfo record3 = countyList.get(k);
                if (record2.getId().intValue() == record3.getParentId().intValue()) {
                    Map map3 = new HashMap();
                    map3.put("value", record3.getId());
                    map3.put("label", record3.getName());
                    list3.add(map3);
                    Map map33 = new HashMap();
                    map33.put("value", record3.getName());
                    map33.put("label", record3.getName());
                    list33.add(map33);
                }
                continue;
            }

            map2.put("children", list3);
            list2.add(map2);
            map22.put("children", list33);
            list22.add(map22);
        }

        for (int i = 0; i < provinceList.size(); i++) {
            RegionInfo record1 = provinceList.get(i);
            Map map1 = new HashMap();
            map1.put("value", record1.getId());
            map1.put("label", record1.getName());
            List<Map> list0 = new ArrayList<>();
            Map map11 = new HashMap();
            map11.put("value", record1.getName());
            map11.put("label", record1.getName());
            List<Map> list00 = new ArrayList<>();

            for (int k = 0; k < list2.size(); k++) {
                Map map4 = list2.get(k);
                if (map4.get("parentId").toString().equals(record1.getId().toString())) {
                    map4.remove("parentId");
                    list0.add(map4);
                    list00.add(list22.get(k));
                }
                continue;
            }

            map1.put("children", list0);
            Constants.REGION_MAP_TO_ID.add(map1);
            map11.put("children", list00);
            Constants.REGION_MAP_TO_NAME.add(map11);
        }
//        JSONArray json1 = new JSONArray(Constants.REGION_MAP_TO_ID);
//        System.out.println("地域信息JSON1：\n" + json1);
//        JSONArray json2 = new JSONArray(Constants.REGION_MAP_TO_NAME);
//        System.out.println("地域信息JSON2：\n" + json2);
    }

    /**
     * 查询省份信息集合
     *
     * @return
     */
    public List<RegionInfo> selectProvinceList() {
        return selectRegionList(null, PROVINCE_LEVEL);
    }

    /**
     * 查询城市信息集合
     *
     * @return
     */
    public List<RegionInfo> selectCityList() {
        return selectRegionList(null, CITY_LEVEL);
    }

    /**
     * 查询地区信息集合
     *
     * @return
     */
    public List<RegionInfo> selectCountyList() {
        return selectRegionList(null, COUNTY_LEVEL);
    }

    /**
     * 查询城市、区县信息集合
     *
     * @param parentId
     * @return
     */
    public List<RegionInfo> selectRegionList(Integer parentId, Integer level) {
        RegionInfoExample example = new RegionInfoExample();
        RegionInfoExample.Criteria c = example.createCriteria();
        if (NullUtil.isNotNullOrEmpty(parentId)) {
            c.andParentIdEqualTo(parentId).andIdNotEqualTo(parentId);
        }
        if (NullUtil.isNotNullOrEmpty(level)) {
            c.andLevelEqualTo(level);
        }
        List<RegionInfo> list = regionInfoMapper.selectByExample(example);
        return list;
    }
}
