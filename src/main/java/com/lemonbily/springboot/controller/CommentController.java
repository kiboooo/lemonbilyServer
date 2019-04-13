package com.lemonbily.springboot.controller;

import com.lemonbily.springboot.entity.Comment;
import com.lemonbily.springboot.mapper.CommentMapper;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CommentController")
public class CommentController extends BaseController<Comment> {

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Override
    @RequestMapping(value = "/insert",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String insert(@RequestBody Comment record) {
        logger.info("----------插入开始-----------");
        if (null == record) {
            logger.error("接收的对象为空");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_INSERT_ERRO_CODE,
                    ResponseCodeUtil.LEMONBILY_OBJECT_NULL_CONTENT, null)
                    .toJSONString();
        }
        if (null == record.getUid() || record.getUid() < 1000) {
            logger.error("接收的Uid是非法id");
            return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_COMMENT_ID_ILLEGAL_CODE,
                    ResponseCodeUtil.LEMONBILY_COMMENT_UID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (commentMapper.insert(record) < 1) {
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
            produces = "application/json;charset=UTF-8"
    )
    public String deleteByID(int comID) {
        if (comID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_DELETE_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_DELETE_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (commentMapper.deleteByComID(comID) < 1) {
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

    /**
     * 无需更改操作函数
     */
    @Override
    public String update(Comment record) {
        return null;
    }

    @Override
    @RequestMapping(value = "/selectAll",
            produces = "application/json;charset=UTF-8"
    )
    public String selectAll() {
        List<Comment> commentList = commentMapper.selectAll();
        if (null == commentList) {
            logger.error("查询不到comment列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil.generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, commentList)
                .toJSONString();
    }

    @Override
    @RequestMapping(value = "/selectByComID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String selectByID(int comID) {
        if (comID <= 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        Comment curComment = commentMapper.selectByComID(comID);
        if (null == curComment) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curComment)
                .toJSONString();
    }


    @RequestMapping(value = "/selectByUID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String selectByUID(int uid) {
        if (uid < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        List<Comment> curCommentList = commentMapper.selectByUID(uid);
        if (null == curCommentList || curCommentList.size() == 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curCommentList)
                .toJSONString();
    }

    @RequestMapping(value = "/selectByUIDAndType",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String selectByUIDAndType(int uid,int comtype) {
        if ( uid < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (!Comment.COMMENT_TYPE.containsKey(comtype)) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_COMMENT_TYPE_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        List<Comment> curCommentList = commentMapper.selectByUIDAndType(uid, comtype);
        if (null == curCommentList || curCommentList.size() == 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curCommentList)
                .toJSONString();
    }

    @RequestMapping(value = "/selectByUIDAndTypeAndToID",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public String selectByUIDAndTypeAndToID( int uid,int comtype,int toid) {
        if ( uid < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if (!Comment.COMMENT_TYPE.containsKey(comtype)) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_COMMENT_TYPE_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        if ( toid <= 0){
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_COMMENT_TOID_ILLEGAL_CONTENT, null)
                    .toJSONString();
        }
        List<Comment> curCommentList = commentMapper.selectByUIDAndTypeAndToID(uid, comtype,toid);
        if (null == curCommentList || curCommentList.size() == 0) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null)
                    .toJSONString();
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, curCommentList)
                .toJSONString();
    }

}
