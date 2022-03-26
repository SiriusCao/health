package com.cao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cao.constant.MessageConstant;
import com.cao.entity.Result;
import com.cao.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/getUsername")
    public Result getUsername(){
        try{
           User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
