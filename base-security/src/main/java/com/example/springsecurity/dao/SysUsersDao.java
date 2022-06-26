package com.example.springsecurity.dao;

import com.example.springsecurity.model.SysUsers;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface SysUsersDao {

    @Select("select * from sys_users where username = #{username}")
    SysUsers findByUserName(@Param("username") String username);

}
