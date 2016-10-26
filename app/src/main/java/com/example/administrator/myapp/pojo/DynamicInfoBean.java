package com.example.administrator.myapp.pojo;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public class DynamicInfoBean {
    public List<DynamicInfo> dynamicInfoList;
    public  static class  DynamicInfo {
        public String dynamicTitle;
        public String dynamicContent;
        public Date dynamicTime;
        public String dynamicZan;
        public String dynamicImg;

        @Override
        public String toString() {
            return "DynamicInfo{" +
                    "dynamicTitle='" + dynamicTitle + '\'' +
                    ", dynamicContent='" + dynamicContent + '\'' +
                    ", dynamicTime=" + dynamicTime +
                    ", dynamicZan='" + dynamicZan + '\'' +
                    ", dynamicImg='" + dynamicImg + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DynamicInfoBean{" +
                "dynamicInfoList=" + dynamicInfoList +
                '}';
    }
}
