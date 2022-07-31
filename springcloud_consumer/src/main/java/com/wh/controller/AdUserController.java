package com.wh.controller;

import com.alibaba.nacos.client.config.utils.MD5;
import com.wh.entity.Result;
import com.wh.feign.AdUserFeign;
import com.wh.pojo.AdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springBoot_User
 * @description: AdUserController
 * @author: wangheng
 * @create: 2022-07-29 11:32
 **/
@RestController
@RequestMapping("/user")
public class AdUserController {
    //注入Feign远程调用接口
    @Autowired
    private AdUserFeign adUserFeign;

    /**
     * 用户登陆
     * @param adUser 用户登陆对象 账号 and 密码
     * @return 通用放回结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody AdUser adUser){
        AdUser login = adUserFeign.login(adUser);
        if(login!=null){
            if (login.getPassword().equals(adUser.getPassword())) {
                //对用户名进行加密 伪造token
                MD5 instance = MD5.getInstance();
                String md5String = instance.getMD5String(adUser.getName());
                return new Result(1,"登陆成功",md5String);
            }else{
                return new Result(0,"密码错误");

            }
        }
        return new Result(0,"用户名不存在");
    }
}
