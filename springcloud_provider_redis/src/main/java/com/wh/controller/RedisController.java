package com.wh.controller;

import com.wh.entity.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: springBoot_User
 * @description: RedisController
 * @author: wangheng
 * @create: 2022-07-29 19:11
 **/
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("add")
    public void addZset() {
        for (int i = 0; i < 10; i++) {
            stringRedisTemplate.boundZSetOps("zset").add("数据" + i, 0);
        }
        System.out.println("over");
    }

    /**
     * 获取redis 中的数据 按照分数 降序返回
     *
     * @return
     */
    @GetMapping("getZset")
    public Map getZset() {
        Map maps=new HashMap();
        List<Map> list = new ArrayList<>();
        Set<String> zset = stringRedisTemplate.boundZSetOps("zset").reverseRange(0, 9);
        for (String s : zset) {
            Double zset1 = stringRedisTemplate.boundZSetOps("zset").score(s);
            Map<String, Object> map = new HashMap<>();
            map.put("name", s);
            map.put("score", zset1);
            list.add(map);
        }
        //获取ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        //首先去redis中取出当前获取到的ip
        String s = stringRedisTemplate.boundValueOps(ip).get();
        //然后判断这个ip是否存在
        if (!StringUtils.isEmpty(s)) {
            //存在 进行判断 是否可以点赞
            Integer isLike = Integer.parseInt(s);
            //判断是否可以点赞
            if (isLike >= 10) {
                //不可以 直接返回 结果
                maps.put("isLike",false);
            }else{
                maps.put("isLike",true);
            }

        }else{
            maps.put("isLike",true);
        }

        maps.put("list",list);

        return maps;
    }

    /**
     * 根据元素名称 给 Score 进行元素自增 +1
     *
     * @param name
     * @return
     */
    @PostMapping("/setZsetLike")
    public Result setZsetLike(@RequestParam("name") String name) {
        //获取ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        //进行ip过滤
        //首先去redis中取出当前获取到的ip
        String s = stringRedisTemplate.boundValueOps(ip).get();
        //然后判断这个ip是否存在
        if (!StringUtils.isEmpty(s)) {
            //存在 进行判断 是否可以点赞
            Integer isLike = Integer.parseInt(s);
            //判断是否可以点赞
            if (isLike >= 10) {
                //不可以 直接返回 结果
                return new Result(2, "每日点赞数量上限");
            } else {
                //先获取到key的过期时间
                // Long expire = stringRedisTemplate.boundValueOps(ip).getExpire();
                //可以 进行 每日点赞数量自增
                stringRedisTemplate.boundValueOps(ip).increment();
                //然后重新设置key过期时间
                // stringRedisTemplate.boundValueOps(ip).expire(expire, TimeUnit.SECONDS);
            }


        } else {
            //不存在 把ip存到redis中 然后进行点赞操作
           stringRedisTemplate.boundValueOps(ip).increment();
            //设置ip过期时间
            stringRedisTemplate.boundValueOps(ip).expire(1, TimeUnit.DAYS);
        }


        try {
             stringRedisTemplate.boundZSetOps("zset").incrementScore(name, 1);
            return new Result(1, "点赞成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(0, "点赞异常");

    }
}
