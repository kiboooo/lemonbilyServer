package com.lemonbily.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.lemonbily.springboot.entity.Like;
import com.lemonbily.springboot.mapper.LikeMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/LikeController")
public class LikeController extends BaseController<Like> {

    @Autowired(required = false)
    LikeMapper likeMapper;

    @Override
    @Transactional
    @RequestMapping(value = "/insert",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public JSONObject insert(@RequestBody Like record) {
        logger.info("----------插入开始-----------");
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null);
        }
        if (null == record.getLuserid() || record.getLuserid() < 1000) {
            logger.error("接收的userID是非法id");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LIKE_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_LIKE_USERID_ILLEGAL_CONTENT, null);
        }


        if (null == record.getLtopalid() || record.getLtopalid() <= 0) {
            logger.error("接收的PALID是非法ID");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_LIKE_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_LIKE_PALID_ILLEGAL_CONTENT, null);
        }

        if (likeMapper.insert(record) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE_CONTENT, null);
        }
        logger.info("----------插入结束-----------");
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, record);
    }

    @Override
    @Transactional
    @RequestMapping(value = "/deleteByLikeID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject deleteByID(int likeID) {
        if (likeID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ID_ILLEGAL_CONTENT, null);
        }
        if (likeMapper.deleteByLikeID(likeID) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, null);
    }

    @Transactional
    @RequestMapping(value = "/deleteByUserIDAndPalID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject deleteByUserIDAndPalID(int userID ,int palID) {
        if (userID < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_LIKE_USERID_ILLEGAL_CONTENT, null);
        }
        if (palID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_LIKE_PALID_ILLEGAL_CONTENT, null);
        }
        if (likeMapper.deleteByUserIDAndPalID(userID, palID) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, null);
    }

    @Override
    public JSONObject update(Like record) {
        return null;
    }

    @Override
    @RequestMapping(value = "/selectAll",
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectAll() {
        List<Like> likeList = likeMapper.selectAll();
        if (null == likeList) {
            logger.error("查询不到Like列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, likeList);
    }

    @Override
    @RequestMapping(value = "/selectByLikeID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByID(int likeID) {
        if (likeID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        Like curLike = likeMapper.selectByLikeID(likeID);
        if (null == curLike){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curLike);
    }

    @RequestMapping(value = "/selectByUserID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByUserID(int userID) {
        if (userID < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        List<Like> curLikeList = likeMapper.selectByUserID(userID);
        if (null == curLikeList || curLikeList.size() == 0){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curLikeList);
    }

    @RequestMapping(value = "/selectByPalID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByPalID(int palID) {
        if (palID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        List<Like> curLikeList = likeMapper.selectByPalID(palID);
        if (null == curLikeList || curLikeList.size() == 0){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curLikeList);
    }

    @RequestMapping(value = "/selectByUserIDAndPalID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByUserIDAndPalID(int userID ,int palID) {
        if (userID < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_LIKE_USERID_ILLEGAL_CONTENT, null);
        }
        if (palID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_LIKE_PALID_ILLEGAL_CONTENT, null);
        }
        Like curLike = likeMapper.selectByUserIDAndPalID(userID, palID);
        if (null == curLike){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curLike);
    }


    @RequestMapping(value = "/selectLikeNumberByPalID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectLikeNumberByPalID(int palID) {
        if (palID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        int curLikeNumber = likeMapper.selectLikeNumberByPalID(palID);
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curLikeNumber);
    }
}
