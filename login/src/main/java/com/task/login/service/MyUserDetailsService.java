package com.task.login.service;

import com.task.login.mapper.UserMapper;
import com.task.login.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserMapper userMapper;
    private final Logger LOGGER = Logger.getLogger("UserDetailsLogger");

    @Autowired
    public MyUserDetailsService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        UserDetails userDetails = null;
        try{
            if(userMapper.getUserByEmail(email).isPresent()){
                user = userMapper.getUserByEmail(email).get();
                userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),getAuthorities(user));
            }else {
                throw new UsernameNotFoundException("nullUser");
            }

        }catch(UsernameNotFoundException usernameNotFoundException){
            LOGGER.log(Level.WARNING,"Nem található ilyen felhasználó!");
        }
        return userDetails;
    }


    private Collection<GrantedAuthority> getAuthorities(User user){
        String[] roles = userMapper.getUserRolesByEmail(user.getEmail());
        return AuthorityUtils.createAuthorityList(roles);
    }



}
