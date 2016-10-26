package com.example.administrator.myapp.pojo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/20.
 */
public class NewestDynamicBean {
    public int status;
    public ArrayList<Dynamic> dynamicList;
    public static class Dynamic{
        public Integer dynamicId;
        public String dynamicTitle;
        public String dynamicContent;
        public String dynamicTime;
        public Integer dynamicZan;
        public String dynamicImg;

        @Override
        public String toString() {
            return "Dynamic{" +
                    "dynamicId=" + dynamicId +
                    ", dynamicTitle='" + dynamicTitle + '\'' +
                    ", dynamicContent='" + dynamicContent + '\'' +
                    ", dynamicTime='" + dynamicTime + '\'' +
                    ", dynamicZan=" + dynamicZan +
                    ", dynamicImg='" + dynamicImg + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewestDynamicBean{" +
                "status=" + status +
                ", dynamicList=" + dynamicList +
                '}';
    }
}
