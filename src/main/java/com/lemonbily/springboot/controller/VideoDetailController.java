package com.lemonbily.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.lemonbily.springboot.entity.*;
import com.lemonbily.springboot.mapper.AccountMapper;
import com.lemonbily.springboot.mapper.CollectMapper;
import com.lemonbily.springboot.mapper.CommentMapper;
import com.lemonbily.springboot.mapper.VideoMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/VideoDetailController")
public class VideoDetailController {

    @Autowired(required = false)
    VideoMapper videoMapper;

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Autowired(required = false)
    AccountMapper accountMapper;

    @Autowired(required = false)
    CollectMapper collectMapper;

    @Transactional
    @RequestMapping(value = "/initVideoDetailData",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject initVideoDetailData(@RequestParam(name = "vid") int vid,
                                          @RequestParam(name = "uid") int uid) {
        if (vid < 1 || uid < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        VideoDetailUIBean videoDetailUIBean = new VideoDetailUIBean();
        List<CommentUIBean> commentUIBeanList = new ArrayList<>();
        Collect curCollect = collectMapper.selectByColIDAndVID(uid, vid);
        videoDetailUIBean.setCollect(curCollect != null);
        List<Comment> curCommentList = commentMapper.selectByToIDAndType(vid, 1);//获取对于视频的评论
        if (curCommentList == null) {
            videoDetailUIBean.setCommentUIBeanList(null);
        }else {
            for (int i = curCommentList.size() - 1; i >= 0; i--) {
                CommentUIBean cuid = new CommentUIBean();
                cuid.setmComment(curCommentList.get(i));
                Account account = accountMapper.selectByID(curCommentList.get(i).getUid());
                cuid.setmAccount(account);
                commentUIBeanList.add(cuid);
            }
            videoDetailUIBean.setCommentUIBeanList(commentUIBeanList);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, videoDetailUIBean);

    }


    @Transactional
    @RequestMapping(value = "/getCollectVideoData",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject getCollectVideoData(@RequestParam(name = "uid") int uid) {
        if (uid < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        List<Collect> curCollectList = collectMapper.selectByColID(uid);
        if (curCollectList == null || curCollectList.size() == 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        List<Video> videoList = new ArrayList<>();
        for (Collect collect : curCollectList) {
            Video video = videoMapper.selectByVID(collect.getVid());
            if (video != null) {
                videoList.add(video);
            }
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, videoList);
    }
}
