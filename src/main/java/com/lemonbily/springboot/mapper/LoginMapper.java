package com.lemonbily.springboot.mapper;

import com.lemonbily.springboot.entity.Login;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface LoginMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbggenerated
     */
    int insert(Login record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbggenerated
     */
    List<Login> selectAll();

    Login selectByID(@Param("id") int id);

    Login selectByPhone(@Param("lphone") String lphone);

    int checkPassWord(@Param("lphone") String lphone,@Param("oldPassWord") String oldPassWord);

    int update(Login login);

    int deleteByID(@Param("id") int id);

    /**
     *  用户登陆生命周期查询
     * @param LoginID 登陆的用户id
     * @return 生命周期：20天，比较当前时间和上次登陆时间的差值；若小于20天，则说明登陆信息有效
     *              否则，需要重新登陆。
     */

    Date liveTimeCheck(@Param("LoginID") int LoginID);

}