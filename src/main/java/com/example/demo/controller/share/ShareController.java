package com.example.demo.controller.share;

import com.example.demo.entity.RegionInfo;
import com.example.demo.service.RegionService;
import com.example.demo.util.AjaxResponse;
import com.example.demo.util.Constants;
import com.example.demo.util.NullUtil;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: demo
 * @description: 共享控制层
 * @author: zhouhuahu
 * @create: 2019-08-17 16:59
 **/
@RestController
@RequestMapping(value = "/api/")
public class ShareController {
    @Autowired
    private RegionService regionService;

    /**
     * 登录拦截，返回错误码
     *
     * @return
     */
    @RequestMapping(value = "error")
    public Object error() {
        return AjaxResponse.msg("0", "当前访问人数过多，请稍后再试");
    }

    /**
     * 查询地域信息集合
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "selectRegionList")
    public Object selectRegionList(String id) {
        List<RegionInfo> list = null;
        if (NullUtil.isNotNullOrEmpty(id)) {
            list = regionService.selectRegionList(Integer.parseInt(id), null);
        } else {
            list = regionService.selectProvinceList();
        }
        return AjaxResponse.success(list);
    }

    /**
     * 查询地域信息JSON
     *
     * @return
     */
    @RequestMapping(value = "selectRegionJson")
    public Object selectRegionJson() {
        return AjaxResponse.success(new JSONArray(Constants.REGION_MAP_TO_NAME).toString());
    }
}
