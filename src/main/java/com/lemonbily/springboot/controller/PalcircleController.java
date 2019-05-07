package com.lemonbily.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.lemonbily.springboot.entity.*;
import com.lemonbily.springboot.mapper.*;
import com.lemonbily.springboot.util.JsonUtil;
import com.lemonbily.springboot.util.ResponseCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/PalcircleController")
public class PalcircleController extends BaseController<Palcircle> {

    @Autowired(required = false)
    PalcircleMapper palcircleMapper;

    @Autowired(required = false)
    BuddyMapper buddyMapper;

    @Autowired(required = false)
    AccountMapper accountMapper;

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Autowired(required = false)
    LikeMapper likeMapper;

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

    @Transactional
    @RequestMapping(value = "/initPalSquareData",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public JSONObject initPalSquareData(@RequestParam("uid") int userID) {
        if (userID < 1000) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_ID_ILLEGAL_CONTENT, null);
        }
        //获取该用户的好友列表
        List<Integer> curBuddyIDList = buddyMapper.selectByUserID(userID);
        //获取该用户的主动点赞信息
        List<Like> curLikeList = likeMapper.selectByUserID(userID);
        //获取动态信息，可以根据对应的算法生成
        List<Palcircle> palList = palcircleMapper.selectAll();
        if (null == palList) {
            logger.error("查询不到PalCircle列表中的数据");
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SELECT_ERRO_CODE,
                            ResponseCodeUtil.LEMONBILY_SELECT_TABLE_NULL_CONTENT, null);
        }
        List<PalSquareBean> list = producePalSquareData(curBuddyIDList, curLikeList, palList);
        if (null == list) {
            return JsonUtil
                    .generateJsonResponse(ResponseCodeUtil.LEMONBILY_PALCIRCLE_ILLEGAL_CODE,
                            ResponseCodeUtil.LEMONBILY_PALCIRCLE_PRODUCE_SQUARE_ERROR_CONTENT, null);
        }
        return JsonUtil
                .generateJsonResponse(ResponseCodeUtil.LEMONBILY_SUCCESS_CODE,
                        ResponseCodeUtil.LEMONBILY_SUCCESS_CODE_CONTENT, list);
    }

    //可以根据一定的规则，生成对应的动态排序
    private List<PalSquareBean> producePalSquareData(List<Integer> curBuddyIDList,
                                                     List<Like> curLikeList, List<Palcircle> palList) {

        List<PalSquareBean> list = new ArrayList<>();
        if (palList == null) {
            return null;
        }
        int maxSize = palList.size();
        int loopNumber = 20;
        if (maxSize < loopNumber) {
            loopNumber = maxSize;
        }
        for (int i = 0; i < loopNumber; i++) {
            Palcircle palcircle = palList.get(maxSize-1 - i);//获取数据库中最新的动态
            if (palcircle != null) {
                PalSquareBean psb = new PalSquareBean();
                psb.setLike(isLikeByPID(curLikeList, palcircle.getPalid()));
                psb.setAttention(isAttentionByUID(curBuddyIDList, palcircle.getPaluserid()));
                psb.setAccount(accountMapper.selectByID(palcircle.getPaluserid()));
                psb.setPalcircle(palcircle);
                psb.setCommentUIBeans(produceCommentBeanByPalID(palcircle.getPalid()));
                list.add(psb);
            }
        }
        return list.size() == 0 ? null : list;
    }

    private List<CommentUIBean> produceCommentBeanByPalID(Integer palid) {
        if (palid <= 0) {
            return null;
        }
        List<CommentUIBean> cbList = new ArrayList<>();
        List<Comment> curCommentList = commentMapper.selectByToIDAndType(palid, 0);
        if (curCommentList == null) {
            return null;
        }
        for (Comment comment : curCommentList) {
            if (comment != null && comment.getUid() >= 1000) {
                CommentUIBean cb = new CommentUIBean();
                cb.setmAccount(accountMapper.selectByID(comment.getUid()));
                cb.setmComment(comment);
                cbList.add(cb);
            }
        }
        return cbList.size() == 0 ? null : cbList;
    }

    private boolean isAttentionByUID(List<Integer> curBuddyIDList, Integer paluserid) {
        if (curBuddyIDList == null) {
            return false;
        }
        return curBuddyIDList.contains(paluserid);
    }

    private boolean isLikeByPID(List<Like> curLikeList, Integer palid) {
        boolean result = false;
        if (curLikeList == null) {
            return false;
        }
        for (Like like : curLikeList) {
            if (like.getLtopalid().equals(palid)) {
                result = true;
            }
        }
        return result;
    }

}
