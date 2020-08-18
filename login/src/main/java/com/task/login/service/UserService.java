package com.task.login.service;

import com.task.login.mapper.UserMapper;
import com.task.login.model.Profile;
import com.task.login.model.UpdateInfo;
import com.task.login.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<Profile> findAllUser(){
        return userMapper.findAllUser();
    }

    public String[] getUserRolesByEmail(String email){
        return userMapper.getUserRolesByEmail(email);
    }

    public Optional<User> getUserByEmail(String email){
        return userMapper.getUserByEmail(email);
    }

    public void updateUser(UpdateInfo updateInfo){
        updateInfo.getUpdatePassword();
        byte[] pwBytes = Base64.getDecoder().decode(updateInfo.getUpdatePassword().getBytes());
        String pw = new String(pwBytes);
        updateInfo.setUpdatePassword(passwordEncoder.encode(pw));
        userMapper.updateUser(updateInfo);
    }

    public void deleteUser(String id){
        userMapper.deleteUserRole(id);
        userMapper.deleteUser(id);
    }
    public void insertNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertNewUser(user.getEmail(), user.getPassword(), user.getFullName(), user.getPhone());
        userMapper.insertNewUserRole(Integer.toString(getUserByEmail(user.getEmail()).get().getId()));
    }
}
