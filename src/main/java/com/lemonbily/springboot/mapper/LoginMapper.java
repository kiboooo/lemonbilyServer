package com.lemonbily.springboot.mapper;

import com.lemonbily.springboot.entity.Login;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Login
 * CRUD表操作
 */
@Mapper
public interface LoginMapper {

    public List<Login> findAll();

//    public int add(Login login);

//    @Delete(" delete from Login where id= #{id} ")
//    public void delete(int id);
//
//    @Select("select * from Login where id= #{id} ")
//    public Login get(int id);
//
//    @Update("update Login set LPassWord=#{LPassWord} where id=#{Id} ")
//    public int updatePassWord(Login login);
//
//    @Update("update Login set LPhone=#{LPhone} where id=#{Id} ")
//    public int updatePhone(Login login);

}
