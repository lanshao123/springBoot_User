package com.wh.controller;

import com.wh.entity.Result;
import com.wh.feign.RedisFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @program: springBoot_User
 * @description: RedisController
 * @author: wangheng
 * @create: 2022-07-29 19:50
 **/
@RequestMapping("/redis")
@RestController
public class RedisController {
    @Autowired
    private RedisFeign redisFeign;
    @GetMapping("getZset")
    public Map getZset(){
        return redisFeign.getZset();
    }
    @PostMapping("/setZsetLike")
    public Result setZsetLike(@RequestParam("name") String name){
        return redisFeign.setZsetLike(name);
    }
}
