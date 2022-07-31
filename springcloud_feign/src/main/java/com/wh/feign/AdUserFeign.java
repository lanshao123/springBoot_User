package com.wh.feign;

import com.wh.pojo.AdUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: springcloud_parent
 * @description: StudentFeign
 * @author: wangheng
 * @create: 2022-07-27 20:58
 **/
@FeignClient("springcloud-provider")
public interface AdUserFeign {
    @PostMapping("/user/login")
    public AdUser login(@RequestBody AdUser adUser);
}
