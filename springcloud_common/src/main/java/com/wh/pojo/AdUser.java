package com.wh.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: springBoot_User
 * @description: AdUser
 * @author: wangheng
 * @create: 2022-07-29 11:17
 **/
@Table(name="ad_user")
public class AdUser {
    @Id
    private Integer id;
    private String name;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
