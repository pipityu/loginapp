package com.task.login.mapper;


import com.task.login.model.Profile;
import com.task.login.model.UpdateInfo;
import com.task.login.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {

    @Select("select id,email,fullName,phone from user")
    @Results(value={
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            //@Result(property = "password", column = "password"),
            @Result(property = "fullName", column = "fullName"),
            @Result(property = "phone", column = "phone")
    })
    List<Profile> findAllUser();

    @Select("select rolename from role where id in(select roleid from user_role where userid=(select id from user where email=#{email}))")
    String[] getUserRolesByEmail(String email);

    @Select("select * from user where email=#{email}")
    @Results(value={
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "fullName", column = "fullName"),
            @Result(property = "phone", column = "phone")
    })
    Optional<User> getUserByEmail(String email);

    @Update("update user set " +
            "fullName = case when length(#{updateName})>0 then #{updateName} else fullName end," +
            "phone = case when length(#{updatePhone})>0 then #{updatePhone} else phone end," +
            "password = case when length(#{updatePassword})>0 is not null then #{updatePassword} else password end where id=#{id}")
    void updateUser(UpdateInfo updateInfo);


    @Delete("delete from user where id=#{id}")
    void deleteUser(String id);

    @Delete("delete from user_role where userid=#{id}")
    void deleteUserRole(String id);

    @Insert("insert into user values(default,#{email}, #{password},#{fullName},#{phone})")
    void insertNewUser(String email, String password, String fullName, String phone);


    @Insert("insert into user_role values(default,#{id},2)")
    void insertNewUserRole(String id);

}