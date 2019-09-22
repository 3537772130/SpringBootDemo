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
     * 加载商品类型列表
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "loadTypePage")
    public Object loadTypePage(@SessionScope(Constants.VUE_USER_INFO) UserInfo user) {
        int count = goodsService.selectGoodsTypeCount(user.getId());
        if (count >= Constants.GOODS_TYPE_COUNT) {
            return AjaxResponse.error("类型创建数量已达上限");
        }
        return AjaxResponse.success();
    }

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
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "uploadGoodsTypeLogo")
    public Object uploadGoodsTypeLogo(@RequestParam("typeLogo") MultipartFile multipartFile) {
        try {
            //校验文件信息
            CheckResult result = CheckFileUtil.checkImageFile(multipartFile, Constants.UPLOAD_PIC_FILE_TYPE);
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
            if (NullUtil.isNullOrEmpty(goodsType.getId())) {
                int count = goodsService.selectGoodsTypeCount(user.getId());
                if (count >= Constants.GOODS_TYPE_COUNT) {
                    return AjaxResponse.error("类型创建数量已达上限");
                }
                goodsType.setTypeIndex(count + 1);
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
     * 更新商品类型排序
     *
     * @param user
     * @param typeId
     * @param sort
     * @return
     */
    @RequestMapping(value = "updateGoodsTypeIndex")
    public Object updateGoodsTypeIndex(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer typeId, String sort) {
        try {
            if (NullUtil.isNullOrEmpty(typeId) || NullUtil.isNullOrEmpty(sort)) {
                return AjaxResponse.error("参数错误");
            }
            int count = goodsService.selectGoodsTypeCount(user.getId());
            if (count > 1) {
                GoodsType type = goodsService.selectGoodsType(typeId, user.getId());
                if (null == type) {
                    return AjaxResponse.error("未找到相关记录");
                }
                Integer num = null;
                if (sort.equals("top") && type.getTypeIndex() - 1 > 0) {
                    num = -1;
                } else if (sort.equals("bot") && type.getTypeIndex() + 1 <= count) {
                    num = 1;
                } else {
                    return AjaxResponse.success("参数错误");
                }
                goodsService.updateGoodsTypeIndex(type, num);
            }
            return AjaxResponse.success();
        } catch (Exception e) {
            log.error("更新商品类型排序出错{}", e);
            return AjaxResponse.error("操作失败");
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
            goods.setCoverSrc("api\\" + goods.getCoverSrc());
            map.put("goods", goods);
            return AjaxResponse.success(map);
        }
        return AjaxResponse.msg("-1", map);
    }

    /**
     * 上传商品封面图片
     *
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "uploadGoodsCover")
    public Object uploadGoodsCover(@RequestParam("cover") MultipartFile multipartFile) {
        try {
            //校验文件信息
            CheckResult result = CheckFileUtil.checkImageFile(multipartFile, Constants.UPLOAD_PIC_FILE_TYPE);
            if (!result.getBool()) {
                return AjaxResponse.error(result.getMsg());
            }
            String fileName = "GC-" + RandomUtil.getRandomStr32() + ".jpg";
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
     * 更新商品信息
     *
     * @param user
     * @param goods
     * @return
     */
    @RequestMapping(value = "updateGoodsInfo")
    public Object updateGoodsInfo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, GoodsInfo goods) {
        try {
            if (null == goods) {
                return AjaxResponse.error("参数错误");
            }
            if (NullUtil.isNullOrEmpty(goods.getGoodsName())) {
                return AjaxResponse.error("商品名称不能为空");
            }
            if (goods.getGoodsName().trim().length() > 100) {
                return AjaxResponse.error("商品名称输入过长");
            } else {
                goods.setGoodsName(goods.getGoodsName().trim());
            }
            if (NullUtil.isNullOrEmpty(goods.getTypeId())) {
                return AjaxResponse.error("请选择商品类型");
            }
            GoodsType type = goodsService.selectGoodsType(goods.getTypeId(), user.getId());
            if (null == type) {
                return AjaxResponse.error("商品类型选择错误");
            }
            if (NullUtil.isNullOrEmpty(goods.getCoverSrc())) {
                return AjaxResponse.error("请上传商品封面图片");
            } else {
                goods.setCoverSrc(goods.getCoverSrc().replace("api\\", ""));
                String oldUrl = null;
                if (NullUtil.isNotNullOrEmpty(goods.getId())) {
                    GoodsInfo record = goodsService.selectGoodsInfo(goods.getId(), user.getId());
                    oldUrl = record.getCoverSrc();
                }
                String newPath = FileUtil.copyGoodsCoverSrc(goods.getCoverSrc(), oldUrl);
                goods.setCoverSrc(newPath);
            }
            goods.setUserId(user.getId());
            goods.setMinPrice(null);
            goods.setMaxPrice(null);
            goodsService.updateGoodsInfo(goods);
            return AjaxResponse.success("提交成功");
        } catch (Exception e) {
            log.error("更新商品信息出错{}", e);
            return AjaxResponse.error("提交失败");
        }
    }

    /**
     * 更新商品排序
     *
     * @param user
     * @param goodsId
     * @param sort
     * @return
     */
    @RequestMapping(value = "updateGoodsIndex")
    public Object updateGoodsIndex(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer goodsId,
                                   Integer typeId, String sort) {
        try {
            if (NullUtil.isNullOrEmpty(goodsId) || NullUtil.isNullOrEmpty(typeId) || NullUtil.isNullOrEmpty(sort)) {
                return AjaxResponse.error("参数错误");
            }
            int count = goodsService.selectGoodsCount(typeId, user.getId());
            if (count > 1) {
                GoodsInfo goods = goodsService.selectGoodsInfo(goodsId, typeId, user.getId());
                if (null == goods) {
                    return AjaxResponse.error("未找到相关记录");
                }
                Integer num = null;
                if (sort.equals("top") && goods.getGoodsIndex().intValue() - 1 > 0) {
                    num = -1;
                } else if (sort.equals("bot") && goods.getGoodsIndex().intValue() + 1 <= count) {
                    num = 1;
                } else {
                    return AjaxResponse.success("参数错误");
                }
                goodsService.updateGoodsIndex(goods, num);
            }
            return AjaxResponse.success();
        } catch (Exception e) {
            log.error("更新商品排序出错{}", e);
            return AjaxResponse.error("操作失败");
        }
    }

    /**
     * 更新商品状态
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "updateGoodsStatus")
    public Object updateGoodsStatus(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer goodsId) {
        if (NullUtil.isNullOrEmpty(goodsId)) {
            return AjaxResponse.error("参数错误");
        }
        GoodsInfo goods = goodsService.selectGoodsInfo(goodsId, user.getId());
        try {
            if (!goods.getStatus()) {
                int fileCount = goodsService.selectFileCount(goodsId, user.getId());
                if (fileCount <= 0) {
                    return AjaxResponse.error("请提交文件(至少一张图片或短视频)");
                }
                int specsCount = goodsService.selectSpecsCount(goodsId, user.getId());
                if (specsCount <= 0) {
                    return AjaxResponse.error("请提交至少一条规格)");
                }
            }
            goodsService.updateGoodsStatus(goods.getId(), goods.getStatus());
            return AjaxResponse.success(goods.getStatus() ? "下架成功" : "发布成功");
        } catch (Exception e) {
            log.error("更新商品状态出错{}", e);
            return AjaxResponse.error(goods.getStatus() ? "下架失败" : "发布失败");
        }
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
     * 上传商品图片文件
     *
     * @param user
     * @param goodsId
     * @param fileId
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "uploadGoodsFileImg")
    public Object uploadGoodsFileImg(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer goodsId, Integer fileId,
                                     @RequestParam("goodsFile") MultipartFile multipartFile) {
        try {
            //校验文件信息
            CheckResult result = CheckFileUtil.checkImageFile(multipartFile, Constants.UPLOAD_PIC_FILE_TYPE);
            if (!result.getBool()) {
                return AjaxResponse.error(result.getMsg());
            }
            if (NullUtil.isNullOrEmpty(goodsId) || NullUtil.isNullOrEmpty(fileId)) {
                return AjaxResponse.error("参数错误");
            }
            ViewGoodsFile record = goodsService.selectFileInfo(fileId, goodsId, user.getId());
            if (null == record) {
                return AjaxResponse.error("未找到相关记录");
            }
            String rootPath = "";
            if (NullUtil.isNullOrEmpty(record.getFileSrc())) {
                String filePath = "static\\images\\goods\\file\\" + goodsId + "\\";
                rootPath = PathUtil.getClassPath(filePath);
                File file = new File(rootPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String fileName = "GI-" + RandomUtil.getRandomStr32() + ".jsp";
                rootPath += fileName;
                record.setFileSrc(filePath.replace("static", "") + fileName);
            } else {
                rootPath = PathUtil.getClassPath("static\\" + record.getFileSrc());
            }
            multipartFile.transferTo(new File(rootPath));
            goodsService.updateGoodsFile(fileId, record.getFileSrc(), true);
            Map map = new HashMap();
            map.put("src", record.getFileSrc() + "?token=" + RandomUtil.getRandomStr32());
            map.put("id", fileId);
            return AjaxResponse.success(map);
        } catch (IOException e) {
            log.error("上传头像出错{}", e);
            return AjaxResponse.success("上传失败");
        }
    }

    /**
     * 上传商品图片文件
     *
     * @param user
     * @param goodsId
     * @param fileId
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "uploadGoodsFileVideo")
    public Object uploadGoodsFileVideo(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer goodsId, Integer fileId,
                                       @RequestParam("goodsFile") MultipartFile multipartFile) {
        try {
            //校验文件信息
            CheckResult result = CheckFileUtil.checkVideoFile(multipartFile);
            if (!result.getBool()) {
                return AjaxResponse.error(result.getMsg());
            }
            if (NullUtil.isNullOrEmpty(goodsId) || NullUtil.isNullOrEmpty(fileId)) {
                return AjaxResponse.error("参数错误");
            }
            ViewGoodsFile record = goodsService.selectFileInfo(fileId, goodsId, user.getId());
            if (null == record) {
                return AjaxResponse.error("未找到相关记录");
            }
            String rootPath = "";
            if (NullUtil.isNullOrEmpty(record.getFileSrc())) {
                String filePath = "static\\images\\goods\\file\\" + goodsId + "\\";
                rootPath = PathUtil.getClassPath(filePath);
                File file = new File(rootPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String fileName = "GV-" + RandomUtil.getRandomStr32() + result.getMsg();
                rootPath += fileName;
                record.setFileSrc(filePath.replace("static", "") + fileName);
            } else {
                rootPath = PathUtil.getClassPath("static\\" + record.getFileSrc());
            }
            multipartFile.transferTo(new File(rootPath));
            goodsService.updateGoodsFile(fileId, record.getFileSrc(), true);
            Map map = new HashMap();
            map.put("src", record.getFileSrc() + "?token=" + RandomUtil.getRandomStr32());
            map.put("id", fileId);
            return AjaxResponse.success(map);
        } catch (IOException e) {
            log.error("上传头像出错{}", e);
            return AjaxResponse.success("上传失败");
        }
    }

    /**
     * 删除商品文件
     *
     * @param user
     * @param goodsId
     * @param fileId
     * @return
     */
    @RequestMapping(value = "deleteGoodsFile")
    public Object deleteGoodsFile(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer goodsId, Integer fileId) {
        try {
            if (NullUtil.isNullOrEmpty(goodsId) || NullUtil.isNullOrEmpty(fileId)) {
                return AjaxResponse.error("参数错误");
            }
            ViewGoodsFile record = goodsService.selectFileInfo(fileId, goodsId, user.getId());
            if (null == record) {
                return AjaxResponse.error("未找到相关记录");
            }
            goodsService.updateGoodsFile(fileId, null, false);
            goodsService.checkGoodsValue(goodsId, user.getId(), false);
            return AjaxResponse.success();
        } catch (Exception e) {
            log.error("删除商品文件出错{}", e);
            return AjaxResponse.error("删除失败");
        }
    }

    /**
     * 加载商品规格列表
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "loadSpecsPage")
    public Object loadSpecsPage(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer goodsId) {
        int count = goodsService.selectSpecsCount(goodsId, user.getId());
        if (count >= Constants.GOODS_SPECS_COUNT) {
            return AjaxResponse.msg("-1", goodsId);
        }
        return AjaxResponse.success(goodsId);
    }

    /**
     * 查询商品规格集合
     *
     * @param user
     * @param specs
     * @return
     */
    @RequestMapping(value = "querySpecsPage")
    public Object querySpecsPage(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, ViewGoodsSpecs specs, HttpServletRequest request) {
        Page page = PageUtil.initPage(request);
        specs.setUserId(user.getId());
        page = goodsService.selectSpecsList(specs, page);
        return AjaxResponse.success(page);
    }

    /**
     * 加载商品规格
     *
     * @param user
     * @param goodsId
     * @param specsId
     * @return
     */
    @RequestMapping(value = "loadGoodsSpecs")
    public Object loadGoodsSpecs(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, Integer goodsId, Integer specsId) {
        if (NullUtil.isNotNullOrEmpty(specsId) && specsId.intValue() != 0) {
            ViewGoodsSpecs record = goodsService.selectSpecsInfo(specsId, goodsId, user.getId());
            if (null != record) {
                return AjaxResponse.success(record);
            }
        }
        return AjaxResponse.error("未找到相关记录");
    }

    /**
     * 更新商品规格
     *
     * @param user
     * @param specs
     * @return
     */
    @RequestMapping(value = "updateGoodsSpecs")
    public Object updateGoodsSpecs(@SessionScope(Constants.VUE_USER_INFO) UserInfo user, GoodsSpecs specs) {
        try {
            if (null == specs) {
                return AjaxResponse.error("参数错误");
            }
            if (NullUtil.isNullOrEmpty(specs.getGoodsId())) {
                return AjaxResponse.error("参数丢失");
            }
            if (NullUtil.isNotNullOrEmpty(specs.getSpecsText()) && specs.getSpecsText().length() > 100) {
                return AjaxResponse.error("商品规格长度过长");
            }
            if (NullUtil.isNullOrEmpty(specs.getSellPrice())) {
                return AjaxResponse.error("出售价格不能为空");
            }
            if (specs.getSellPrice().doubleValue() > 99999.99d) {
                return AjaxResponse.error("出售价格不能高于99999.99");
            }
            if (NullUtil.isNotNullOrEmpty(specs.getDiscount()) && specs.getDiscount().doubleValue() > 100) {
                return AjaxResponse.error("商品折扣不能大于100");
            }
            if (NullUtil.isNotNullOrEmpty(specs.getDiscountDescribe()) && specs.getDiscountDescribe().length() > 500) {
                return AjaxResponse.error("折扣描述长度过长");
            }
            if (NullUtil.isNullOrEmpty(specs.getId())) {
                int count = goodsService.selectGoodsSpecsCount(specs.getGoodsId(), user.getId());
                if (count >= Constants.GOODS_SPECS_COUNT) {
                    return AjaxResponse.error("商品规格创建数量已达上限");
                }
            }
            if (NullUtil.isNotNullOrEmpty(specs.getSpecsSrc())) {
                specs.setSpecsSrc(specs.getSpecsSrc().replace("api\\", ""));
                String oldUrl = null;
                if (NullUtil.isNotNullOrEmpty(specs.getId())) {
                    ViewGoodsSpecs record = goodsService.selectSpecsInfo(specs.getId(), specs.getGoodsId(), user.getId());
                    oldUrl = record.getSpecsSrc();
                }
                String newPath = FileUtil.copyGoodsCoverSrc(specs.getSpecsSrc(), oldUrl);
                specs.setSpecsSrc(newPath);
            }
            boolean bool = (NullUtil.isNullOrEmpty(specs.getId()) && !specs.getSpecsStatus()) ? false : true;
            specs.setActualPrice(specs.getSellPrice() * specs.getDiscount() / 100);
            goodsService.updateGoodsSpecs(specs, user.getId());
            if (bool) {
                goodsService.checkGoodsValue(specs.getGoodsId(), user.getId(), true);
            }
            return AjaxResponse.success("提交成功");
        } catch (Exception e) {
            log.error("跟新商品规格出错{}", e);
            return AjaxResponse.error("提交失败");
        }
    }
}
