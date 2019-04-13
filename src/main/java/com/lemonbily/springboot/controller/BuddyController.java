package com.lemonbily.springboot.controller;

import com.lemonbily.springboot.entity.Account;
import com.lemonbily.springboot.entity.Buddy;
import com.lemonbily.springboot.mapper.AccountMapper;
import com.lemonbily.springboot.mapper.BuddyMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/BuddyController")
public class BuddyController extends BaseController<Buddy>{

    @Autowired(required = false)
    BuddyMapper buddyMapper;

    @Autowired(required = false)
    AccountMapper accountMapper;


    /**
     * 关注操作
     *
     * 要求：
     *  1）Userid < Buddyid ,这样插入保证一致性。
     *  2）进入该接口的关注对象，不再考究其双方的好友关系是否已经存在。
     *  3）所以再调用该接口的时候，务必保证双方并非好友关系
     *
     * @param record 好友对象
     * @return {
     *              "msg":
     *              "code":
     *              "data": { }
     *          }
     *
     */
    @Override
    @Transactional
    @RequestMapping(value = "/insert",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String insert(@RequestBody Buddy record) {
        logger.info("----------插入开始-----------");
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        logger.info(record.toString());
        if (null == record.getBuddyid() || null == record.getUserid() ||
                record.getUserid() < 1000 || record.getBuddyid() < 1000
                || record.getBuddyid().equals(record.getUserid())) {

            logger.error("接收的UserID是非法id");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_BUDDY_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_BUDDY_USERID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (record.getUserid() > record.getBuddyid()) {
            swap(record);
        }
        if (buddyMapper.insert(record) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        logger.info("----------插入结束-----------");
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, record)
                .toJSONString();
    }

    @Override
    @Transactional
    @RequestMapping(value = "/deleteByBID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String deleteByID(int bid) {
        if (bid <= 0) {
            return  JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (buddyMapper.deleteByBID(bid) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        return  JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, null)
                .toJSONString();
    }

    @Transactional
    @RequestMapping(value = "/deleteByUIDandBuddlyID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String deleteByUIDandBuddlyID( int userID, int buddyID) {
        if (userID < 1000 || buddyID < 1000 || buddyID == userID) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_BUDDY_USERID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (buddyMapper.deleteByUIDandBuddlyID(userID, buddyID) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        return  JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, null)
                .toJSONString();
    }

    @Override
    public String update(Buddy record) {
        return null;
    }

    @Override
    @RequestMapping(value = "/selectAll",
            produces = "application/json;charset=UTF-8"
    )
    public String selectAll() {
        List<Buddy> buddyList = buddyMapper.selectAll();
        if (null == buddyList) {
            logger.error("查询不到Buddy列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, buddyList)
                .toJSONString();
    }

    @Override
    @RequestMapping(value = "/selectByBID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String selectByID(int bid) {
        if (bid <= 0) {
            return  JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        Buddy curBuddy = buddyMapper.selectByBID(bid);
        if (null == curBuddy){
            return  JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curBuddy)
                .toJSONString();
    }
    @Transactional
    @RequestMapping(value = "/selectByUserID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String selectByUserID(int userID) {
        if (userID < 1000) {
            return  JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        List<Integer> curBuddyIDList = buddyMapper.selectByUserID(userID);
        if (null == curBuddyIDList || curBuddyIDList.size() == 0){
            return  JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        List<Account> curAccountList = new ArrayList<>();
        for (Integer uid : curBuddyIDList) {
            if (uid < 1000) continue;
            Account accountBody = accountMapper.selectByID(uid);
            if (accountBody == null) continue;
            curAccountList.add(accountBody);
        }
        if (curAccountList.size() <= 0) {
            return  JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curAccountList)
                .toJSONString();
    }

    private void swap(Buddy buddy) {
        int temp = buddy.getUserid();
        buddy.setUserid(buddy.getBuddyid());
        buddy.setBuddyid(temp);
    }
}
