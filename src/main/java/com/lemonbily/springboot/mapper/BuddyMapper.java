package com.lemonbily.springboot.mapper;

import com.lemonbily.springboot.entity.Buddy;
import java.util.List;

public interface BuddyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buddy
     *
     * @mbggenerated
     */
    int insert(Buddy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buddy
     *
     * @mbggenerated
     */
    List<Buddy> selectAll();
}