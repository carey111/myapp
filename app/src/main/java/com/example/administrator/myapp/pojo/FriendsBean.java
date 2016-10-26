package com.example.administrator.myapp.pojo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/17.
 */
public class FriendsBean {
    public int status;
    public ArrayList<Friends> friendsList;

    public static class Friends{
        public Integer friendId;
        public String userName;
        public String userPhotoImg;
        public Integer sex;
        public String interestLabel;
        public String birthday;

        @Override
        public String toString() {
            return "Friends{" +
                    "friendId=" + friendId +
                    ", userName='" + userName + '\'' +
                    ", userPhotoImg='" + userPhotoImg + '\'' +
                    ", sex=" + sex +
                    ", interestLabel='" + interestLabel + '\'' +
                    ", birthday=" + birthday +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FriendsBean{" +
                "status=" + status +
                ", friendsList=" + friendsList +
                '}';
    }
}
