package com.wh.service.impl;

import com.wh.mapper.AdUserMapper;
import com.wh.pojo.AdUser;
import com.wh.service.AdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: springBoot_User
 * @description: AdUserServiceImpl
 * @author: wangheng
 * @create: 2022-07-29 13:42
 **/
@Service
@SuppressWarnings("ALL")
public class AdUserServiceImpl implements AdUserService {
    @Autowired
    private AdUserMapper adUserMapper;
    @Override
    public AdUser login(AdUser adUser) {

        return adUserMapper.selectAdUserByName(adUser.getName());
    }
}
