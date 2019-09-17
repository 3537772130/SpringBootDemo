package com.example.demo.controller.user;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.*;
import com.example.demo.service.GoodsService;
import com.example.demo.util.*;
import com.example.demo.util.file.FileUtil;
import com.example.demo.util.file.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: SpringBootDemo
 * @description: 用户商品控制类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-09-16 17:48
 **/
@RestController
@RequestMapping(value = "/api/user/goods/")
public class UserGoodsController {
    private static final Logger log = LoggerFactory.getLogger(UserGoodsController.class);
    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询用户商品分类列表
     *
     * @param user
     * @param name
     * @param request
     * @return
     */
    @RequestMapping(value = "queryTypePage")
    public Object queryTypePage(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, String name, Integer status, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        page = goodsService.selectTypePage(user.getId(), name, status, page);
        return AjaxResponse.success(page);
    }

    /**
     * 加载商品类型信息
     *
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "loadGoodsType")
    public Object loadGoodsType(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer id) {
        GoodsType goodsType = goodsService.selectGoodsType(id, user.getId());
        if (null == goodsType) {
            return AjaxResponse.error("未找到相关信息");
        }
        goodsType.setTypeLogo("api\\" + goodsType.getTypeLogo());
        return AjaxResponse.success(goodsType);
    }

    /**
     * 上传商品类型图标
     *
     * @param userInfo
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "uploadGoodsTypeLogo")
    public Object uploadGoodsTypeLogo(@SessionScope(Constants.VUE_USER_INFO) UserInfo userInfo, @RequestParam("typeLogo") MultipartFile multipartFile) {
        try {
            //校验文件信息
            CheckResult result = CheckFileUtil.checkPicFile(multipartFile, Constants.UPLOAD_PIC_FILE_TYPE);
            if (!result.getBool()) {
                return AjaxResponse.error(result.getMsg());
            }
            String fileName = "GT-" + RandomUtil.getRandomStr32() + ".jpg";
            String filePath = "static\\images\\upload\\";
            String rootPath = PathUtil.getClassPath(filePath);
            multipartFile.transferTo(new File(rootPath + fileName));
            String src = filePath + fileName;
            return AjaxResponse.success(src.replace("static", "api"));
        } catch (IOException e) {
            log.error("上传头像出错{}", e);
            return AjaxResponse.success("上传失败");
        }
    }


    /**
     * 更新商品类型信息
     *
     * @param user
     * @param goodsType
     * @return
     */
    @RequestMapping(value = "updateGoodsType")
    public Object updateGoodsType(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, GoodsType goodsType) {
        try {
            if (null == goodsType) {
                return AjaxResponse.error("参数错误");
            }
            if (NullUtil.isNullOrEmpty(goodsType.getTypeName())) {
                return AjaxResponse.error("类型名称不能为空");
            }
            if (goodsType.getTypeName().trim().length() > 30) {
                return AjaxResponse.error("类型名称过长");
            } else {
                goodsType.setTypeName(goodsType.getTypeName().trim());
            }
            if (NullUtil.isNotNullOrEmpty(goodsType.getTypeLogo())) {
                goodsType.setTypeLogo(goodsType.getTypeLogo().replace("api\\", ""));
                String oldUrl = null;
                if (NullUtil.isNotNullOrEmpty(goodsType.getId())) {
                    GoodsType record = goodsService.selectGoodsType(goodsType.getId(), user.getId());
                    oldUrl = record.getTypeLogo();
                }
                String newPath = FileUtil.copyGoodsTypeLogo(goodsType.getTypeLogo(), oldUrl);
                goodsType.setTypeLogo(newPath);
            }
            goodsType.setUserId(user.getId());
            goodsService.updateGoodsType(goodsType);
            return AjaxResponse.success("提交成功");
        } catch (Exception e) {
            log.error("更新商品类型信息出错{}", e);
            return AjaxResponse.error("提交失败");
        }
    }


    /**
     * 查询商品类型集合
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "queryTypeList")
    public Object queryTypeList(@SessionScope(Constants.VUE_USER_INFO) UserInfo user) {
        List<GoodsType> list = goodsService.selectTypeList(user.getId());
        return AjaxResponse.success(list);
    }

    /**
     * 分页查询用户商品列表
     *
     * @param user
     * @param goods
     * @param request
     * @return
     */
    @RequestMapping(value = "queryInfoPage")
    public Object queryInfoToPage(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, ViewGoodsInfo goods, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        goods.setUserId(user.getId());
        page = goodsService.selectInfoPage(goods, page);
        return AjaxResponse.success(page);
    }

    /**
     * 加载商品信息
     *
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "loadGoodsInfo")
    public Object loadGoodsInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer id) {
        Map map = new HashMap();
        List<GoodsType> list = goodsService.selectTypeList(user.getId());
        map.put("typeList", list);
        if (NullUtil.isNotNullOrEmpty(id) && id.intValue() != 0) {
            GoodsInfo goods = goodsService.selectGoodsInfo(id, user.getId());
            map.put("goods", goods);
            return AjaxResponse.success(map);
        }
        return AjaxResponse.msg("-1", map);
    }

    public Object updateGoodsInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, GoodsInfo goods) {
        if (null == goods) {

        }
        return AjaxResponse.error("提交失败");
    }

    /**
     * 查询商品文件集合
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "queryFileList")
    public Object queryFileList(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer goodsId) {
        List<ViewGoodsFile> list = goodsService.selectFileList(goodsId, user.getId());
        if (NullUtil.isNullOrEmpty(list)) {
            return AjaxResponse.error("未找到相关记录");
        }
        return AjaxResponse.success(list);
    }

    /**
     * 查询商品规格集合
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "querySpecsList")
    public Object querySpecsList(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer goodsId) {
        List<ViewGoodsSpecs> list = goodsService.selectSpecsList(goodsId, user.getId());
        if (NullUtil.isNullOrEmpty(list)) {
            return AjaxResponse.error("未找到相关记录");
        }
        return AjaxResponse.success(list);
    }

}
