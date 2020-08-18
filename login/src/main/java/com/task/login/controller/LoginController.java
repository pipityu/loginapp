package com.task.login.controller;

import com.task.login.model.AuthRequest;
import com.task.login.model.AuthResponse;
import com.task.login.service.JwtService;
import com.task.login.service.MyUserDetailsService;
import com.task.login.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;


@RestController
public class LoginController {


    private UserService userService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    public LoginController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService){
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
    }


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest, HttpServletResponse response){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException badCredentialsException){
            System.err.println("Nem megfelelő felhasználónév vagy jelszó");
            return ResponseEntity.status(401).body("Nem megfelelő felhasználónév vagy jelszó");
        }


        final String jwt = jwtService.generateToken(myUserDetailsService.loadUserByUsername(authRequest.getUsername()));
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1000*60*60);
        response.addCookie(cookie);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/user/pwcheck")
    public ResponseEntity<?> pwcheck(@RequestBody AuthRequest authRequest, HttpServletResponse response){
        System.out.println(authRequest.getUsername());
        System.out.println(authRequest.getPassword());
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException badCredentialsException){
            System.err.println("Nem megfelelő felhasználónév vagy jelszó");
            return ResponseEntity.status(401).body("Nem megfelelő felhasználónév vagy jelszó");
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwt",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @GetMapping(value = "/getrole", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> getRole(Principal principal){
        return Collections.singletonMap("role", userService.getUserRolesByEmail(principal.getName())[0]);
    }

}
