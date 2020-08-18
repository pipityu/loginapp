package com.task.login.config;

import com.task.login.filter.JwtRequestFilter;
import com.task.login.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class LoginConfiguration extends WebSecurityConfigurerAdapter {

    private MyUserDetailsService myUserDetailService;
    private PasswordEncoder passwordEncoder;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public LoginConfiguration(MyUserDetailsService myUserDetailService, PasswordEncoder passwordEncoder, JwtRequestFilter jwtRequestFilter){
        this.myUserDetailService = myUserDetailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtRequestFilter = jwtRequestFilter;
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(myUserDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowCredentials(true).allowedMethods("GET, POST, OPTIONS, PUT");

            }
        };
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] staticResources = {"/resources/**","/static/**","/css/**","/img/**","/js/**", "/webjars/**","/assets/**","/files/**"};
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//nincs session, állapotmentes a szerver
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/","/login","/auth","/getrole").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and().cors()
                .and()
                .logout(logout->logout.permitAll().logoutSuccessHandler((request, response, authentication)->{response.setStatus(HttpServletResponse.SC_OK);}).deleteCookies("jwt"))
        ;
    }

}

