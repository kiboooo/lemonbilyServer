package com.lemonbily.springboot.mapper;

import com.lemonbily.springboot.entity.Collect;
import java.util.List;

public interface CollectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table collect
     *
     * @mbggenerated
     */
    int insert(Collect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table collect
     *
     * @mbggenerated
     */
    List<Collect> selectAll();
}