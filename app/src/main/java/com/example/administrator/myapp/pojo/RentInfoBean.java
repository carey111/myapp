package com.example.administrator.myapp.pojo;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class RentInfoBean {
    public List<RentInfo> rentInfoList;
    public  static class  RentInfo {

        public int collectId;
        public String roomType;
        public String roomArea;
        public Double roomRent;
        public  String photoImg;


        @Override
        public String toString() {
            return "RentInfo{" +
                    "collectId=" + collectId +
                    ", roomType='" + roomType + '\'' +
                    ", roomArea='" + roomArea + '\'' +
                    ", roomRent=" + roomRent +
                    ", photoImg='" + photoImg + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RentInfoBean{" +
                "rentInfoList=" + rentInfoList +
                '}';
    }
}
