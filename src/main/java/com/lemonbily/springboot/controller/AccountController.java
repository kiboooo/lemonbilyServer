package com.lemonbily.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.lemonbily.springboot.bean.CommonBean;
import com.lemonbily.springboot.entity.Account;
import com.lemonbily.springboot.mapper.AccountMapper;
import com.lemonbily.springboot.util.FileUtil;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.activation.ActivationID;
import java.util.List;

@RestController
@RequestMapping("/AccountController")
public class AccountController extends BaseController<Account> {

    @Autowired(required = false)
    AccountMapper accountMapper;

    @Override
    @RequestMapping(value = "/insert",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject insert(@RequestBody Account record) {
        logger.info("----------插入个人页开始-----------");
        if (null == record) {
            logger.error("插入对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null);
        }
        logger.info(record.toString());
        if (null == record.getAid() || record.getAid() < 1000) {
            logger.error("插入对象的ID是非法ID" + record.getAid());
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null);
        }

        if (accountMapper.insert(record) < 1) {
            logger.error("数据库插入语句出错");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE_CONTENT, null);
        }
        logger.info("----------插入个人页开始结束-----------");
        logger.info("插入成功");
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_SUCCESS_CONTENT, record);
    }

    @Override
    @Transactional
    @RequestMapping(value = "/deleteByID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject deleteByID(int aid) {
        if (aid < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null);
        }
        String delMsg = null;
        Account delAccount = accountMapper.selectByID(aid);
        if (null == delAccount || accountMapper.deleteByID(aid) < 1) {
            delMsg = "获取的Account对象为空,无法删除";
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, delMsg);
        }

        if (FileUtil.delFile(delAccount.getAavatar())) {
            delMsg = "头像图片删除完毕";
        } else {
            delMsg = "头像图片删除失败，或路径不存在";
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, delMsg);
    }


    @Override
    @RequestMapping(value = "/update",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject update(@RequestBody Account record) {
        if (null == record) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null);
        }
        if (null == record.getAid() || record.getAid() < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null);
        }
        if (accountMapper.update(record) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE_CONTENT, null);
        }

        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_ACCOUNT_UPDATE_SUCCESS_CONTENT, record);
    }

    /**
     * 上传头像
     *
     * @param aId   上传者id
     * @param image 上传的图片文件对象
     * @return 返回图片在服务器上的地址。
     */

    @Transactional
    @RequestMapping(value = "/uploadAvatar",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject uploadAvatar(@RequestParam("aid") Integer aId,
                               @RequestParam("avatarImage") MultipartFile image) {

        logger.info("----文件上传开始----");
        if (null == image || null == aId || image.isEmpty()) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CODE,
                    ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CONTENT, null);
        }
        logger.info("aid =" + aId + "  image" + image.getOriginalFilename());
        if (aId < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, image.getOriginalFilename());
        }

        //TODO: 本地环境和线上的环境，对于资源的存储地址；
//        服务器环境
        String avatarUrl = FileUtil.upload(image, aId.toString(),
                CommonBean.SERVER_AVATAR_RELATIVE_PATH, FileUtil.USER_AVATAR_PREFIX);

        //debug环境
//        String avatarUrl = FileUtil.upload(image, aId.toString(),
//                CommonBean.DEBUG_SERVER_AVATAR_RELATIVE_PATH, FileUtil.USER_AVATAR_PREFIX);
        if (avatarUrl == null) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE_CONTENT, image.getOriginalFilename());
        } else{
            logger.info(avatarUrl);
            Account account = accountMapper.selectByID(aId);
            if (account != null) {
                account.setAavatar(avatarUrl);
                accountMapper.update(account);
            }
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                            ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, account);
        }
    }


    @RequestMapping(value = "/uploadAvatars",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject uploadAvatars(@RequestParam("aid") Integer aId,
                               @RequestParam("avatarImages") MultipartFile[] image) {

        logger.info("----文件上传开始----");
        if (null == image || null == aId || image.length <=0) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CODE,
                    ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CONTENT, null);
        }
        if (aId < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null);
        }
//        服务器环境
        List AvatarUrl = FileUtil.upload(image, aId.toString(),
                CommonBean.SERVER_AVATAR_RELATIVE_PATH, FileUtil.USER_AVATAR_PREFIX);

        //debug环境
//        List AvatarUrl = FileUtil.upload(image, aId.toString(),
//                CommonBean.DEBUG_SERVER_AVATAR_RELATIVE_PATH, FileUtil.USER_AVATAR_PREFIX);
        if (AvatarUrl == null) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE_CONTENT, null);
        } else{
            logger.info(AvatarUrl.toString());
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                            ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, AvatarUrl);
        }
    }

    @Override
    @RequestMapping(value = "/selectAll",
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectAll() {
        List<Account> list = accountMapper.selectAll();
        if (list == null) {
            logger.error("查询不到Account列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, list);
    }


    @Override
    @RequestMapping(value = "/selectByID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByID(int aid) {
        if (aid < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null);
        }
        Account account = accountMapper.selectByID(aid);
        if (account == null) {
           return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, account);
    }
}
