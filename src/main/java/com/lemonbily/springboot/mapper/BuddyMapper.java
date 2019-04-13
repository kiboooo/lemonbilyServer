package com.lemonbily.springboot.mapper;

import com.lemonbily.springboot.entity.Buddy;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 好友表 1) userid1->userid2 和 userid2->userid1 是一样的记录，不要重复添加
 * 2) 为了快速判断两个人是不是好友，可以在程序层插入数据前加一个限制 userid1 < userid2
 * 3) 为了快速得到一个人的好友列表，查询时用 UNION ALL，代替or会更高性能，
 *      因为取可能重复的值所以不能用UNION
 * 4) 如果为了再高效，加入缓存层（Redis 或 Memcached）
 */
public interface BuddyMapper {

    int insert(Buddy record);

    List<Buddy> selectAll();

    int deleteByBID(@Param("bid") int bid);

    int deleteByUIDandBuddlyID(@Param("userID") int userID, @Param("buddyID")int buddyID);

    Buddy selectByBID(@Param("bid") int bid);

    List<Integer> selectByUserID(@Param("userID") int userID);
}