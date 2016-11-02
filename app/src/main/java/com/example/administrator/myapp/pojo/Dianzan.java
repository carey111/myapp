package com.example.administrator.myapp.pojo;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/10/23 0023.
 */
public class Dianzan {

    public Integer dianzanId;
    public User user;
    public Integer dongtaiId;
    public Timestamp dianzanTime;


    public Dianzan(Integer dongtaiId, Timestamp dianzanTime, User user) {
        this.dongtaiId = dongtaiId;
        this.dianzanTime = dianzanTime;
        this.user = user;
    }
}
