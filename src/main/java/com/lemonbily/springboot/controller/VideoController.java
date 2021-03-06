package com.lemonbily.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.lemonbily.springboot.entity.Collect;
import com.lemonbily.springboot.entity.Video;
import com.lemonbily.springboot.entity.VideoDetailUIBean;
import com.lemonbily.springboot.mapper.AccountMapper;
import com.lemonbily.springboot.mapper.CollectMapper;
import com.lemonbily.springboot.mapper.CommentMapper;
import com.lemonbily.springboot.mapper.VideoMapper;
import com.lemonbily.springboot.util.FileUtil;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/VideoController")
public class VideoController extends BaseController<Video> {

    @Autowired(required = false)
    VideoMapper videoMapper;

    @Override
    @RequestMapping(value = "/insert",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public JSONObject insert(@RequestBody  Video record) {
        logger.info("----------插入开始-----------");
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null);
        }
        if (null == record.getSid() || record.getSid() <= 0) {
            logger.error("接收的sid是非法id");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_VIDEO_ID_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_VIDEO_INSERT_SID_ILLEGAL_CODE_CONTENT, null);
        }

        if (videoMapper.insert(record) < 1) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE_CONTENT, null);
        }
        logger.info("----------插入结束-----------");
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, record);
    }

    @Override
    @Transactional
    @RequestMapping(value = "/deleteByID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject deleteByID( int vid) {
        if (vid <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ID_ILLEGAL_CONTENT, null);
        }
        String delMsg = null;
        Video delVideo = videoMapper.selectByVID(vid);
        if (null == delVideo ||  videoMapper.deleteByVID(vid) < 1) {
            delMsg = "获取的video对象为空,无法删除";
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE_CONTENT, delMsg);
        }
        if (FileUtil.delFile(delVideo.getVpath())
                && FileUtil.delFile(delVideo.getVpicture()) ) {
            delMsg = "视频,图片资源删除完毕";
        } else {
            delMsg = "视频,图片资源删除失败，或路径不存在";
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
    public JSONObject update(@RequestBody Video record) {
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null);
        }
        if (null == record.getVid() || record.getVid() <= 0) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_VIDEO_ID_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_VIDEO_UPDATE_VID_ILLEGAL_CODE_CONTENT, null);
        }
        if (videoMapper.update(record) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE_CONTENT, null);
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, record);
    }


    @RequestMapping(value = "/updateAddPlay",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public JSONObject updateAddPlay(int vid) {
        if (vid <= 0) {
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_VIDEO_ID_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_VIDEO_UPDATE_VID_ILLEGAL_CODE_CONTENT, null);
        }
        if (videoMapper.updateAddPlay(vid) < 1) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_UPDATE_ERRO_CODE_CONTENT, null);
        }

        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, "vid is " + vid);
    }


    @Override
    @RequestMapping(value = "/selectAll",
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectAll() {
        List<Video> videoList = videoMapper.selectAll();
        if (null == videoList) {
            logger.error("查询不到video列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, videoList);
    }

    @Override
    @RequestMapping(value = "/selectByVID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectByID(int vid) {
        if (vid <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        Video curVideo = videoMapper.selectByVID(vid);
        if (null == curVideo){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curVideo);
    }


    @RequestMapping(value = "/selectBySID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject selectBySID(int sid) {
        if (sid <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        List<Video> curVideoList = videoMapper.selectBySID(sid);
        if (null == curVideoList || curVideoList.size() == 0){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curVideoList);
    }
}
