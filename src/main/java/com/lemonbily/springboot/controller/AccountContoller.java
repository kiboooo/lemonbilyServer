package com.lemonbily.springboot.controller;

import com.lemonbily.springboot.entity.Account;
import com.lemonbily.springboot.mapper.AccountMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/AccountContoller")
public class AccountContoller extends BaseController<Account> {

    @Autowired(required = false)
    AccountMapper accountMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass()); //日志对象
    @Override
    @RequestMapping(value = "/insert",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String insert(@RequestBody Account record) {
        logger.info("----------插入个人页开始-----------");
        if (null == record) {
            logger.error("插入对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CODE,
                    ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (null == record.getAId() || record.getAId() < 1000) {
            logger.error("插入对象的ID是非法ID");
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
    @RequestMapping(value = "/deleteByID/{AId}",
            produces = "application/json;charset=UTF-8"
    )
    public String deleteByID(@PathVariable("AId") int AId) {
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
    public String update(@RequestBody Account record ) {
        if (null == record) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CODE,
                    ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (record.getAId() < 1000) {
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
                ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_SUCCESS_CONTENT, record)
                .toJSONString();
    }


    @RequestMapping(value = "/updateAvatar",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String updateAvatar( @RequestParam("name") Account record, @RequestParam("avatarImage") MultipartFile image) {

        if (null == record) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CODE,
                    ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (record.getAId() < 1000) {
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
                ResponseCodeUtil.LEMONBILY_ACCOUNT_INSERT_SUCCESS_CONTENT, record)
                .toJSONString();
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
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
       return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, list)
                .toJSONString();
    }

    @Override
    @RequestMapping(value = "/insert/{AId}",
            produces = "application/json;charset=UTF-8"
    )
    public String selectByID(@PathVariable("AId") int AId) {
        if (AId < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_ACCOUNT_AID_FAIL_CODE_CONTENT, null)
                    .toJSONString();
        }
        Account account = accountMapper.selectByID(AId);
        if (account == null) {
            JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_FAIL_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, account)
                .toJSONString();
    }
}
