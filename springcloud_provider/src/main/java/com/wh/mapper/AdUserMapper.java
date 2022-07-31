package com.wh.mapper;

import com.wh.pojo.AdUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: springBoot_User
 * @description: AdUserMapper
 * @author: wangheng
 * @create: 2022-07-29 13:39
 **/
@Mapper
public interface AdUserMapper extends tk.mybatis.mapper.common.Mapper<AdUser> {
    AdUser selectAdUserByName(String name);
}
