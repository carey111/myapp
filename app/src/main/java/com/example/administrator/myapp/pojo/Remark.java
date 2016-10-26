package com.example.administrator.myapp.pojo;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class Remark {
    public Integer remarkId;
    public String  remarkContent;
    public Timestamp remarkTime;
    public User user;
    public boolean isEnd;
    public Integer fatherRemarkId;
    public Integer dongtaiId;
    public User fatheruser;
}
