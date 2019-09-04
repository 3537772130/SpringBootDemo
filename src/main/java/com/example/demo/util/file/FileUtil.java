package com.example.demo.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;

/**
 * @program: SpringBootDemo
 * @description: 文件处理工具类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-08-26 11:32
 **/
public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 删除文件夹下所有文件
     *
     * @param path
     * @return
     */
    public static void deleteDir(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {//判断是否待删除目录是否存在
                System.err.println("The dir are not exists!");
            }
            String[] content = file.list();//取得当前目录下所有文件和文件夹
            for (String name : content) {
                File temp = new File(path, name);
                if (temp.isDirectory()) {//判断是否是目录
                    deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                    temp.delete();//删除空目录
                } else {
                    if (!temp.delete()) {//直接删除文件
                        System.err.println("Failed to delete " + name);
                    }
                }
            }
        } catch (Exception e) {
            log.error("删除文件夹出错{}", e);
        }
    }

    /**
     * 删除CLASS下的文件
     *
     * @param filePath
     * @param fileName
     */
    public static void deleteClassFile(String filePath, String fileName) {
        try {
            String rootPath = PathUtil.getClassPath(filePath);
            File file = new File(rootPath + fileName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            log.error("删除CLASS下文件出错{}", e);
        }
    }

    /**
     * 复制文件，并删除原文件
     *
     * @param oldPath
     * @param oldFileName
     * @param newPath
     * @param newFileName
     */
    public static String copyFile(String oldPath, String oldFileName, String newPath, String newFileName) {
        try {
            // 获取复制的文件
            String oldRootPath = PathUtil.getClassPath(oldPath);
            File file1 = new File(oldRootPath + oldFileName);
            if (file1.exists()) {
                // 获取目标文件，如果存在，则删除
                String newRootPath = PathUtil.getClassPath(newPath);
                File file2 = new File(newRootPath + newFileName);
                if (file2.exists()) {
                    file2.delete();
                }
                // 复制文件至目标目录，并删除原文件
                Files.copy(file1.toPath(), file2.toPath());
                file1.delete();
                return newPath.replace("static\\", "") + newFileName;
            }
        } catch (Exception e) {
            log.error("复制文件出错{}", e);
        }
        return null;
    }
}
