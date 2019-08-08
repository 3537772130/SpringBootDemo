package com.example.demo.util;

import java.io.File;

/**
 * @program: SpringBootDemo
 * @description: 获取项目在服务其中的真实路径的工具类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-08-05 09:31
 **/
public class GetWebProjectRealPathTool {

    //获取项目的根路径
    private static String classPath = GetWebProjectRealPathTool.class.getResource("/").getPath();

    //对项目的根路径进行解析，拿到项目路径
    public static String getRootPath(String sourcePath) {
        String rootPath = "";
        //windows下
        if ("\\".equals(File.separator)) {
            rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes")) + sourcePath;
            rootPath = rootPath.replace("/", "\\");
        }
        //linux下
        if ("/".equals(File.separator)) {
            rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes")) + sourcePath;
            rootPath = rootPath.replace("\\", "/");
        }
        return rootPath;
    }

    public static String getClassPath(String filePath) {
        String rootPath = "";
        //windows下
        if ("\\".equals(File.separator)) {
            rootPath = classPath.substring(1, classPath.length()) + filePath;
            rootPath = rootPath.replace("/", "\\");
        }
        //linux下
        if ("/".equals(File.separator)) {
            rootPath = classPath.substring(0, classPath.length()) + filePath;
            rootPath = rootPath.replace("\\", "/");
        }
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdir();
        }
        return rootPath;
    }

    public static void main(String[] arge) {
        System.out.println(getClassPath("\\resources\\static\\images\\head-portrait\\"));
    }
}
