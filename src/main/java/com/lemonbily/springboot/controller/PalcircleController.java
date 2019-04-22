package com.lemonbily.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.lemonbily.springboot.entity.Palcircle;
import com.lemonbily.springboot.mapper.PalcircleMapper;
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
@RequestMapping("/PalcircleController")
public class PalcircleController extends BaseController<Palcircle> {

    @Autowired(required = false)
    PalcircleMapper palcircleMapper;

    @Override
    @Transactional
    @RequestMapping(value = "/insert",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public JSONObject insert(@RequestBody Palcircle record) {
        logger.info("----------插入开始-----------");
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null);
        }
        if (null == record.getPaluserid() || record.getPaluserid() < 1000) {
            logger.error("接收的userID是非法id");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_PALCIRCLE_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_PALCIRCLE_PALUSER_ID_ILLEGAL_CONTENT, null);
        }
        if (palcircleMapper.insert(record) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE_CONTENT, null);
        }
        logger.info("----------插入结束-----------");
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, record);
    }

    @Override
    @Transactional
    @RequestMapping(value = "/deleteByPalID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject deleteByID(int palID) {
        if (palID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ID_ILLEGAL_CONTENT, null);
        }
        if (palcircleMapper.deleteByPalID(palID) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, null);
    }

    @Override
    @RequestMapping(value = "/update",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject update(@RequestBody Palcircle record) {
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null);
        }
        if (null == record.getPalid() || record.getPalid() <= 0) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_PALCIRCLE_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_PALCIRCLE_PALID_ILLEGAL_CONTENT, null);
        }
        if (palcircleMapper.update(record) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE_CONTENT, null);
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, record);
    }

    @RequestMapping(value = "/updateLikeNumber",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject updateLikeNumber(int palID ,int likeType) {
        if ( palID <= 0) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_PALCIRCLE_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_PALCIRCLE_PALID_ILLEGAL_CONTENT, null);
        }
        if (palcircleMapper.updateLikeNumber(palID, likeType > 0 ? 1 : -1) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE_CONTENT, null);
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, "likeType is :" + likeType);
    }

    @Override
    @RequestMapping(value = "/selectAll",
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectAll() {
        List<Palcircle> palList = palcircleMapper.selectAll();
        if (null == palList) {
            logger.error("查询不到PalCircle列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, palList);
    }

    @Override
    @RequestMapping(value = "/selectByPalID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByID(int palID) {
        if (palID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        Palcircle curPalCircle = palcircleMapper.selectByPalID(palID);
        if (null == curPalCircle){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curPalCircle);
    }


    @RequestMapping(value = "/selectByPalUserID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
   public JSONObject selectByPalUserID (int palUserID){
        if (palUserID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        List<Palcircle> curPalList = palcircleMapper.selectByPalUserID(palUserID);
        if (null == curPalList || curPalList.size() == 0){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curPalList);
   }

}
