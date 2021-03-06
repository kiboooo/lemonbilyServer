package com.lemonbily.springboot.mapper;

import com.lemonbily.springboot.entity.Like;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeMapper {

    int insert(Like record);

    List<Like> selectAll();

    Like selectByLikeID(@Param("likeID") int likeID);

    List<Like> selectByUserID(@Param("userID") int userID);

    List<Like> selectByPalID(@Param("palID")  int palID);

    Like selectByUserIDAndPalID(@Param("userID") int userID, @Param("palID")int palID);

    int selectLikeNumberByPalID(@Param("palID") int palID);

    int deleteByLikeID(@Param("likeID") int likeID);

    int deleteByUserIDAndPalID(@Param("userID") int userID, @Param("palID")int palID);
}