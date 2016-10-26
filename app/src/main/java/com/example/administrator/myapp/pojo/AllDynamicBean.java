package com.example.administrator.myapp.pojo;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class AllDynamicBean {
    public int status;
    public List<Dynamics> dynamicsList;

    public static class Dynamics{
        public Integer dynamicId;
        public String dynamicTitle;
        public String dynamicContent;
        public String dynamicTime;
        public Integer dynamicZan;
        public String dynamicImg;
        public String hasDemand;

        @Override
        public String toString() {
            return "Dynamics{" +
                    "dynamicId=" + dynamicId +
                    ", dynamicTitle='" + dynamicTitle + '\'' +
                    ", dynamicContent='" + dynamicContent + '\'' +
                    ", dynamicTime='" + dynamicTime + '\'' +
                    ", dynamicZan=" + dynamicZan +
                    ", dynamicImg='" + dynamicImg + '\'' +
                    ", hasDemand='" + hasDemand + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AllDynamicBean{" +
                "status=" + status +
                ", dynamicsList=" + dynamicsList +
                '}';
    }
}
