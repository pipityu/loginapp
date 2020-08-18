package com.task.login.controller;

import com.task.login.model.Profile;
import com.task.login.model.UpdateInfo;
import com.task.login.model.User;
import com.task.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/profile")
    public Profile userDetails(Principal principal){//@AuthenticationPrincipal UserDetails principal){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = userService.getUserByEmail(principal.getName()).orElse(new User("null"));
        Profile profile = new Profile(user.getId(),user.getFullName(),user.getEmail(),user.getPhone());
        return profile;
    }

    @PostMapping("/user/updateuser")
    public ResponseEntity<?> updateUser(@RequestBody UpdateInfo updateInfo){
        userService.updateUser(updateInfo);
        return ResponseEntity.ok(HttpStatus.OK);

    }
    @PostMapping("user/admin/deleteuser")
    public ResponseEntity<?> deleteUser(@RequestBody String id){
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("user/admin/newuser")
    public ResponseEntity<?> insertNewUser(@RequestBody User user){
        userService.insertNewUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
