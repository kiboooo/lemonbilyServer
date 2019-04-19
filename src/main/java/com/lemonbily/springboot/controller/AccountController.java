package com.lemonbily.springboot.controller;

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
    public String insert(@RequestBody Account record) {
        logger.info("----------插入个人页开始-----------");
        if (null == record) {
            logger.error("插入对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        logger.info(record.toString());
        if (null == record.getAid() || record.getAid() < 1000) {
            logger.error("插入对象的ID是非法ID" + record.getAid());
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE,
                    ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        if (accountMapper.insert(record) < 1) {
            logger.error("数据库插入语句出错");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        logger.info("----------插入个人页开始结束-----------");
        logger.info("插入成功");
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_SUCCESS_CONTENT, record)
                .toJSONString();
    }

    @Override
    @RequestMapping(value = "/deleteByID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String deleteByID(int AId) {
        if (AId < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        if (accountMapper.deleteByID(AId) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, null)
                .toJSONString();
    }

    @Override
    @RequestMapping(value = "/update",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String update(@RequestBody Account record) {
        if (null == record) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (null == record.getAid() || record.getAid() < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        if (accountMapper.update(record) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_ACCOUNT_UPDATE_SUCCESS_CONTENT, record)
                .toJSONString();
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
    public String uploadAvatar(@RequestParam("aid") Integer aId,
                               @RequestParam("avatarImage") MultipartFile image) {

        logger.info("----文件上传开始----");
        if (null == image || null == aId || image.isEmpty()) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CODE,
                    ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        logger.info("aid =" + aId + "  image" + image.getOriginalFilename());
        if (aId < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, image.getOriginalFilename())
                    .toJSONString();
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
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE_CONTENT, image.getOriginalFilename())
                    .toJSONString();
        } else{
            logger.info(avatarUrl);
            Account account = accountMapper.selectByID(aId);
            if (account != null) {
                account.setAavatar(avatarUrl);
                accountMapper.update(account);
            }
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                            ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, avatarUrl)
                    .toJSONString();
        }
    }


    @RequestMapping(value = "/uploadAvatars",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String uploadAvatars(@RequestParam("aid") Integer aId,
                               @RequestParam("avatarImages") MultipartFile[] image) {

        logger.info("----文件上传开始----");
        if (null == image || null == aId || image.length <=0) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CODE,
                    ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (aId < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
//        服务器环境
//        String AvatarUrl = FileUtil.upload(image, aId.toString(),
//                CommonBean.SERVER_AVATAR_RELATIVE_PATH, FileUtil.USER_AVATAR_PREFIX, image.getOriginalFilename());

        //debug环境
        List AvatarUrl = FileUtil.upload(image, aId.toString(),
                CommonBean.DEBUG_SERVER_AVATAR_RELATIVE_PATH, FileUtil.USER_AVATAR_PREFIX);
        if (AvatarUrl == null) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_UPLOAD_FILE_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        } else{
            logger.info(AvatarUrl.toString());
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                            ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, AvatarUrl)
                    .toJSONString();
        }
    }

    @Override
    @RequestMapping(value = "/selectAll",
            produces = "application/json;charset=UTF-8"
    )
    public String selectAll() {
        List<Account> list = accountMapper.selectAll();
        if (list == null) {
            logger.error("查询不到Account列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, list)
                .toJSONString();
    }

    @Override
    @RequestMapping(value = "/selectByID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String selectByID(int AId) {
        if (AId < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        Account account = accountMapper.selectByID(AId);
        if (account == null) {
            JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, account)
                .toJSONString();
    }
}
