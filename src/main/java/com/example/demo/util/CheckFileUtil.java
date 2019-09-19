package com.example.demo.util;

import com.example.demo.entity.CheckResult;
import com.example.demo.util.file.FileUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: SpringBootDemo
 * @description: 检查文件工具类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-08-05 09:24
 **/
public class CheckFileUtil {

    /**
     * 校验图片文件
     *
     * @param file
     * @param fileType
     * @return
     */
    public static CheckResult checkImageFile(MultipartFile file, String fileType) {
        if (file == null) {
            return new CheckResult("未解析到文件");
        }
        String fileName = file.getOriginalFilename();
        if (NullUtil.isNullOrEmpty(fileName) && file.getSize() == 0) {
            return new CheckResult("图片内容为空");
        }
        if (file.getSize() > 5 * 1048576) {
            return new CheckResult("文件大于5MB");
        }
        String type = file.getContentType();
        if (fileType.indexOf(type) < 0) {
            return new CheckResult("不支持的文件类型");
        }
        return new CheckResult();
    }

    /**
     * 校验压缩文件
     *
     * @param file
     * @param fileType
     * @return
     */
    public static CheckResult checkTemplateFile(MultipartFile file, String fileType) {
        if (file == null) {
            return new CheckResult("未解析到文件");
        }
        String fileName = file.getOriginalFilename();
        if (NullUtil.isNullOrEmpty(fileName) && file.getSize() == 0) {
            return new CheckResult("压缩包内容为空");
        }
        if (file.getSize() > 2 * 1048576) {
            return new CheckResult("文件大于2MB");
        }
        String type = file.getContentType();
        if (fileType.indexOf(type) < 0) {
            return new CheckResult("不支持的文件类型");
        }
        return new CheckResult();
    }

    /**
     * 校验视频文件
     *
     * @param file
     * @return
     */
    public static CheckResult checkVideoFile(MultipartFile file) {
        if (file == null) {
            return new CheckResult("未解析到文件");
        }
        String fileName = file.getOriginalFilename();
        if (NullUtil.isNullOrEmpty(fileName) && file.getSize() == 0) {
            return new CheckResult("视频内容为空");
        }
        if (file.getSize() > 10 * 1048576) {
            return new CheckResult("文件大于10MB");
        }
        if (!FileUtil.isVedioFile(fileName)) {
            return new CheckResult("不支持的文件类型");
        }
        String type = file.getContentType();
        String specs = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return new CheckResult(true, specs);
    }

}
