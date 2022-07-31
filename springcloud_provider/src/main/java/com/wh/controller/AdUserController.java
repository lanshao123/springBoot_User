package com.wh.controller;

import com.wh.pojo.AdUser;
import com.wh.service.AdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springBoot_User
 * @description: controller
 * @author: wangheng
 * @create: 2022-07-29 11:20
 **/
@RestController
@RequestMapping("/user")
public class AdUserController {
    @Autowired
    private AdUserService adUserService;
    @PostMapping("/login")
    public AdUser login(@RequestBody AdUser adUser){
        return adUserService.login(adUser);
    }
}
