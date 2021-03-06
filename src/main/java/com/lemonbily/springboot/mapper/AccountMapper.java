package com.lemonbily.springboot.mapper;

import com.lemonbily.springboot.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbggenerated
     */
    int insert(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbggenerated
     */
    List<Account> selectAll();

    Account selectByID(@Param("aid") int aid);

    int update(Account account);

    int deleteByID(@Param("aid") int aid
    );
}