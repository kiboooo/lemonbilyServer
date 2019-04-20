package com.lemonbily.springboot.controller;

import com.lemonbily.springboot.entity.Series;
import com.lemonbily.springboot.mapper.SeriesMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SeriesController")
public class SeriesController extends BaseController<Series> {

    @Autowired(required = false)
    SeriesMapper seriesMapper;

    @Override
    @RequestMapping(value = "/insert",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String insert(@RequestBody Series record) {
        logger.info("----------插入开始-----------");
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (null == record.getStype() || !Series.SERIES_TYPE.containsKey(record.getStype())) {
            logger.error("接收的type是非法类型");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SERIES_ID_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_SERIES_TEPY_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (seriesMapper.insert(record) < 1) {
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
    @RequestMapping(value = "/deleteByID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String deleteByID(int sid) {
        if (sid <= 0) {
            return  JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (seriesMapper.deleteBySID(sid) < 1) {
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
    @RequestMapping(value = "/update",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String update(@RequestBody Series record) {
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (null == record.getSeriesid() || record.getSeriesid() <= 0) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SERIES_ID_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_SERIES_UPDATE_SID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (!Series.SERIES_TYPE.containsKey(record.getStype())){
            logger.error("接收的type是非法类型");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SERIES_ID_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_SERIES_TEPY_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (seriesMapper.update(record) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, record)
                .toJSONString();
    }

    @Override
    @RequestMapping(value = "/selectAll",
            produces = "application/json;charset=UTF-8")
    public String selectAll() {
        List<Series> seriesList = seriesMapper.selectAll();
        if (null == seriesList) {
            logger.error("查询不到series列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, seriesList)
                .toJSONString();
    }

    @Override
    @RequestMapping(value = "/selectBySID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String selectByID(int sid) {
        if (sid <= 0) {
            return  JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        Series curSeries = seriesMapper.selectBySID(sid);
        if (null == curSeries){
            return  JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curSeries)
                .toJSONString();
    }
}
