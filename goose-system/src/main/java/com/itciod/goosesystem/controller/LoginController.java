package com.itciod.goosesystem.controller;

import com.itciod.goosesystem.pojo.Result;
import com.itciod.goosesystem.pojo.User;
import com.itciod.goosesystem.service.UserService;
import com.itciod.goosesystem.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录LoginController
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("员工登录:{}",user);
        User u = userService.login(user);

        //登录成功，生成令牌，下发令牌
        if (u != null){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id", u.getUserId());
            claims.put("username", u.getUsername());

            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }




        //登录失败，返回错误信息


        return Result.error("用户名或密码错误");
    }

















}
