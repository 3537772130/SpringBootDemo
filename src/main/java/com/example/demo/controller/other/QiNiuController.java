package com.example.demo.controller.other;

import com.example.demo.util.AjaxResponse;
import com.example.demo.util.RandomUtil;
import com.example.demo.util.qiniu.QiNiuUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: demo
 * @description: 七牛云凭证控制类
 * @author: zhouhuahu
 * @create: 2019-09-28 14:28
 **/
@RestController
@RequestMapping(value = "/api/qiniu/")
public class QiNiuController {

    /**
     * 上传文件到公共空间token 10S有效
     *
     * @param response
     * @return
     */
    @RequestMapping("getPublicToken")
    public Object getPublicToken(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String key = "api\\public\\" + RandomUtil.getTimeStamp();
        String uploadToken = QiNiuUtil.getUploadTokenPublic();
        Map<String, String> result = new HashMap<>();
        result.put("qnt", uploadToken);
        result.put("qnk", key);
        return AjaxResponse.success(result);
    }

    /**
     * 上传七牛图片空间 10s 有效 Token
     *
     * @param response
     * @return
     */
    @RequestMapping("getImageToken")
    public Object getImageToken(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String key = "api\\image\\" + RandomUtil.getTimeStamp();
        String uploadToken = QiNiuUtil.getUploadTokenImage(key);
        Map<String, String> result = new HashMap<>();
        result.put("qnt", uploadToken);
        result.put("qnk", key);
        return AjaxResponse.success(result);
    }

    /**
     * 上传七牛音频空间 10s 有效 Token
     *
     * @param response
     * @return
     */
    @RequestMapping("getAudioToken")
    public Object getAudioToken(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String key = "api\\audio\\" + RandomUtil.getTimeStamp();
        String uploadToken = QiNiuUtil.getUploadTokenAudio(key);
        Map<String, String> result = new HashMap<>();
        result.put("qnt", uploadToken);
        result.put("qnk", key);
        return AjaxResponse.success(result);
    }

    /**
     * 上传七牛视频空间 10s 有效 Token
     *
     * @param response
     * @return
     */
    @RequestMapping("getVideoToken")
    public Object getVideoToken(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String key = "api\\video\\" + RandomUtil.getTimeStamp();
        String uploadToken = QiNiuUtil.getUploadTokenVideo(key);
        Map<String, String> result = new HashMap<>();
        result.put("qnt", uploadToken);
        result.put("qnk", key);
        return AjaxResponse.success(result);
    }

    /**
     * 上传七牛压缩包空间 10s 有效 Token
     *
     * @param response
     * @return
     */
    @RequestMapping("getZipToken")
    public Object getZipToken(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String key = "api\\zip\\" + RandomUtil.getTimeStamp();
        String uploadToken = QiNiuUtil.getUploadTokenZip(key);
        Map<String, String> result = new HashMap<>();
        result.put("qnt", uploadToken);
        result.put("qnk", key);
        return AjaxResponse.success(result);
    }

}
