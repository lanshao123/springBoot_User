package com.wh.feign;

import com.wh.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@FeignClient("springcloud-providerRedis")
public interface RedisFeign {
    @GetMapping("/redis/getZset")
    public Map getZset();
    @PostMapping("/redis/setZsetLike")
    public Result setZsetLike(@RequestParam("name") String name);
}
