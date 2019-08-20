package com.example.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: SpringBootDemo
 * @description: 常量
 * @author: Mr.ZhouHuaHu
 * @create: 2019-06-19 17:18
 **/
public class Constants {
    // 时间格式
    public static final String DATE_TIME_JDK = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_JODD = "YYYY-MM-DD hh:mm:ss";
    public static final String DATE_TIME_YMDHM = "YYYY-MM-DD hh:mm";
    public static final String DEFAULT_DATE_FORMAT_STAMP = "YYYYMMDDhhmmss";
    public static final String DATE_TIME_YMD = "yyyy-MM-dd";
    public static final String DATE_TIME_ZH = "YYYY年MM月DD日";
    public static final String JODD_FMT_YYYYMMDD = "YYYYMMDD";
    public static final String JODD_FMT_HHMMSS = "hhmmss";
    //图形验证码
    public static final String FIGURE_CODE = "figure_code";
    // 后台管理用户信息
    public static final String WEB_MANAGER_INFO = "EXAMPLE_WEB_MANAGER_INFO";
    // 前端普通用户信息
    public static final String VUE_USER_INFO = "EXAMPLE_VUE_USER_INFO";

    // 小程序上传
    // logo路径
    public static final String APPLET_LOGO = "applet_logo_";
    // 营业执照照片路径
    public static final String LICENSE_SRC = "license_src_";

    // 文件格式
    public static final String UPLOAD_PIC_FILE_TYPE = "image/png,image/jpeg";//上传图片文件类型
    public static final String UPLOAD_TEMPLATE_COMPRESS_FILE_TYPE = "application/x-zip-compressed,application/x-7z-compressed,application/x-gzip";//上传模板压缩文件类型

    // 地域信息集合
    // v-k：ID-NAME
    public static final List<Map> REGION_MAP_TO_ID = new ArrayList<>();
    // v-k：NAME-NAME
    public static final List<Map> REGION_MAP_TO_NAME = new ArrayList<>();

    public static final Map<String, String> MONTH_MAP = new HashMap<String, String>() {
        {
            put("01", "一月");
            put("02", "二月");
            put("03", "三月");
            put("04", "四月");
            put("05", "五月");
            put("06", "六月");
            put("07", "七月");
            put("08", "八月");
            put("09", "九月");
            put("10", "十月");
            put("11", "十一月");
            put("12", "十二月");
        }
    };

}
