package com.lemonbily.springboot.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class FileUtil {

    public static final String USER_AVATAR_PREFIX = "avatar";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 获取上传文件的文件名后缀，以便于确定是那种类型的文件
     *
     * @param fileName 上传的文件名
     * @return 返回文件名的后缀；
     */
    private static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 格式化上传的文件名 ： 前缀 + 上传用户id + 上传用户name + 文件后缀
     *
     * @param prefix   上传文件的前缀
     * @param fileName 原本上传的文件名
     * @return 格式化后的文件名
     */
    public static String formatFileName(String id, String prefix, String fileName) {

        return UUID.randomUUID().toString() +
                prefix + "_" +
                id + "_" +
                sdf.format(System.currentTimeMillis()) +
                getSuffix(fileName);
    }

    /**
     * 上传文件
     *
     * @param file     通过表单传递过来的文件对象
     * @param path     文件存放在服务器上的地址
     * @param fileName 上传文件的原始文件名
     * @return 返回上传文件后地址，供读取
     */
    public static String upload(MultipartFile file, String path, String fileName) {
        return upload(file, null, path, null, fileName);
    }

    /**
     * 上传文件
     *
     * @param file     通过表单传递过来的文件对象
     * @param path     文件存放在服务器上的地址
     * @param prefix   上传文件的前缀，用于区别服务器上的文件
     * @param fileName 上传文件的原始文件名
     * @return 服务器存储的地址
     */

    public static String upload(MultipartFile file, String id,
                                String path, String prefix, String fileName) {
        String realPath = path;
        if (id == null) {
            realPath += "/" + fileName;
        } else {
            if (prefix == null) {
                prefix = "lemon";
            }
            realPath = path + "/" + id + "/" + formatFileName(id, prefix, fileName);
        }

        File curFile = new File(realPath);

        if (!curFile.getParentFile().exists()) {
            if (!curFile.getParentFile().mkdirs()) {
                return null;
            }
        }

        try {
            file.transferTo(curFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return curFile.getPath();
    }
}
