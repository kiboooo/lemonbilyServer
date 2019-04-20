package com.lemonbily.springboot.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static final String USER_AVATAR_PREFIX = "avatar";
    private static SimpleDateFormat sdfMillis = new SimpleDateFormat("yyyyMMddHHmmssSS");
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("util.FileUtil");

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

        return prefix + "_" +
                id + "_" +
                sdfMillis.format(System.currentTimeMillis()) +
                getSuffix(fileName);
    }

    /**
     * 上传文件
     *
     * @param file 通过表单传递过来的文件对象
     * @param path 文件存放在服务器上的地址
     * @return 返回上传文件后地址，供读取
     */
    public static String upload(MultipartFile file, String path) {
        return upload(file, null, path, null);
    }

    /**
     * 上传文件，单文件上传
     *
     * @param file   通过表单传递过来的文件对象
     * @param id     上传者的id
     * @param path   文件存放在服务器上的地址
     * @param prefix 上传文件的前缀，用于区别服务器上的文件
     * @return 服务器存储的地址
     */

    public static String upload(MultipartFile file, String id,
                                String path, String prefix) {
        String realPath = path;
        if (null == file) {
            return null;
        }
        if (id == null) {
            realPath += File.separator + file.getOriginalFilename();
        } else {
            if (prefix == null) {
                prefix = "lemon";
            }
            logger.info("path = " + path);
            logger.info("prefix = " + prefix);
            logger.info("fileName = " + file.getOriginalFilename());
            logger.info("realPath = " + realPath);
            realPath = path + File.separator + id + File.separator + formatFileName(id, prefix, file.getOriginalFilename());
        }
        logger.info(realPath);
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

    /**
     * 上传文件，多文件上传
     *
     * @param file 多个文件宿主
     * @param id     上传者的id
     * @param path   文件存放在服务器上的地址
     * @param prefix 上传文件的前缀，用于区别服务器上的文件
     * @return 返回众多上传结果
     */
    public static List<JSONObject> upload(MultipartFile[] file, String id, String path, String prefix) {
        List<JSONObject> filePaths = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String curPath = upload(multipartFile, id, path, prefix);
            if (null == curPath) {
                filePaths.add(JsonUtil
                        .generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE,
                                ResponseCodeUtil.LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE_CONTENT,
                                multipartFile == null ? null : multipartFile.getOriginalFilename()));
            }else {
                filePaths.add(JsonUtil
                        .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curPath));
            }
        }
        return filePaths;
    }

    /**
     * 删除文件
     *
     * @param AbsPath 需要删除文件的绝对地址
     * @return 是否删除成功
     */
    public static boolean delFile(String AbsPath) {
        if (null == AbsPath || AbsPath.equals("")) {
            return false;
        }
        File delf = new File(AbsPath);
        return delf.exists() && delf.delete();
    }


}
