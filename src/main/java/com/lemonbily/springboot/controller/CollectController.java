package com.lemonbily.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.lemonbily.springboot.entity.Collect;
import com.lemonbily.springboot.mapper.CollectMapper;
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
@RequestMapping("/CollectController")
public class CollectController extends BaseController<Collect> {

    @Autowired(required = false)
    CollectMapper collectMapper;

    @Override
    @Transactional
    @RequestMapping(value = "/insert",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public JSONObject insert(@RequestBody Collect record) {
        logger.info("----------插入开始-----------");
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null);
        }
        if (null == record.getColid() || record.getColid() < 1000) {
            logger.error("接收的ColID是非法id");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_COLLECT_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_COLLECT_USERID_ILLEGAL_CONTENT, null);
        }

        if (null == record.getVid() || record.getVid() <= 0) {
            logger.error("接收的VID是非法ID");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_COLLECT_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_COLLECT_VID_ILLEGAL_CONTENT, null);
        }

        if (collectMapper.insert(record) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE_CONTENT, null);
        }
        logger.info("----------插入结束-----------");
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, record);
    }

    @Override
    @Transactional
    @RequestMapping(value = "/deleteByColPID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public JSONObject deleteByID(int colPID) {
        if (colPID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ID_ILLEGAL_CONTENT, null);
        }
        if (collectMapper.deleteByColPID(colPID) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, null);
    }

    @Transactional
    @RequestMapping(value = "/deleteByColIDAndVID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject deleteByColIDAndVID(int colID, int vid) {
        if (colID < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_COLLECT_USERID_ILLEGAL_CONTENT, null);
        }
        if (vid <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_COLLECT_VID_ILLEGAL_CONTENT, null);
        }
        if (collectMapper.deleteByColIDAndVID(colID, vid) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, null);
    }

    @Override
    public JSONObject update(Collect record) {
        return null;
    }

    @Override
    @RequestMapping(value = "/selectAll",
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectAll() {
        List<Collect> collectList = collectMapper.selectAll();
        if (null == collectList) {
            logger.error("查询不到Collect列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, collectList);
    }

    @Override
    @RequestMapping(value = "/selectByColPID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByID(int colPID) {
        if (colPID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        Collect curCollect = collectMapper.selectByColPID(colPID);
        if (null == curCollect){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curCollect);

    }

    @RequestMapping(value = "/selectByColID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByColID(int colID) {
        if (colID < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        List<Collect> curCollectList = collectMapper.selectByColID(colID);
        if (null == curCollectList || curCollectList.size() == 0){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curCollectList);
    }

    @RequestMapping(value = "/selectByVID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByVID(int vid) {
        if (vid <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_COLLECT_VID_ILLEGAL_CONTENT, null);
        }
        List<Collect> curCollectList = collectMapper.selectByVID(vid);
        if (null == curCollectList || curCollectList.size() == 0){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curCollectList);
    }

    @RequestMapping(value = "/selectByColIDAndVID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByColIDAndVID(int colID ,int vid) {
        if (colID < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_COLLECT_USERID_ILLEGAL_CONTENT, null);
        }
        if (vid <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_COLLECT_VID_ILLEGAL_CONTENT, null);
        }
        Collect curCollect = collectMapper.selectByColIDAndVID(colID, vid);
        if (null == curCollect){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curCollect);
    }


    @RequestMapping(value = "/selectCollectNumberByVID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectCollectNumberByVID(int vid) {
        if (vid <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        int curLikeNumber = collectMapper.selectCollectNumberByVID(vid);
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curLikeNumber);
    }
}
