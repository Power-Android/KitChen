package com.power.kitchen.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class AreaListsBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : [{"area_id":"2","name":"北京市","up_id":"1"},{"area_id":"3","name":"天津市","up_id":"1"},{"area_id":"4","name":"河北省","up_id":"1"},{"area_id":"5","name":"山西省","up_id":"1"},{"area_id":"6","name":"内蒙古自治区","up_id":"1"},{"area_id":"7","name":"辽宁省","up_id":"1"},{"area_id":"8","name":"吉林省","up_id":"1"},{"area_id":"9","name":"黑龙江省","up_id":"1"},{"area_id":"10","name":"上海市","up_id":"1"},{"area_id":"11","name":"江苏省","up_id":"1"},{"area_id":"12","name":"浙江省","up_id":"1"},{"area_id":"13","name":"安徽省","up_id":"1"},{"area_id":"14","name":"福建省","up_id":"1"},{"area_id":"15","name":"江西省","up_id":"1"},{"area_id":"16","name":"山东省","up_id":"1"},{"area_id":"17","name":"河南省","up_id":"1"},{"area_id":"18","name":"湖北省","up_id":"1"},{"area_id":"19","name":"湖南省","up_id":"1"},{"area_id":"20","name":"广东省","up_id":"1"},{"area_id":"21","name":"广西壮族自治区","up_id":"1"},{"area_id":"22","name":"海南省","up_id":"1"},{"area_id":"23","name":"重庆市","up_id":"1"},{"area_id":"24","name":"四川省","up_id":"1"},{"area_id":"25","name":"贵州省","up_id":"1"},{"area_id":"26","name":"云南省","up_id":"1"},{"area_id":"27","name":"西藏自治区","up_id":"1"},{"area_id":"28","name":"陕西省","up_id":"1"},{"area_id":"29","name":"甘肃省","up_id":"1"},{"area_id":"30","name":"青海省","up_id":"1"},{"area_id":"31","name":"宁夏回族自治区","up_id":"1"},{"area_id":"32","name":"新疆维吾尔自治区","up_id":"1"}]
     */

    private String status;
    private String info;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area_id : 2
         * name : 北京市
         * up_id : 1
         */

        private String area_id;
        private String name;
        private String up_id;

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUp_id() {
            return up_id;
        }

        public void setUp_id(String up_id) {
            this.up_id = up_id;
        }
    }
}
