package com.power.kitchen.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class BrandListBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : [{"brand_id":"6","name":"飞利浦","frist":"F"},{"brand_id":"1","name":"海尔","frist":"H"},{"brand_id":"4","name":"LG","frist":"L"},{"brand_id":"2","name":"美的","frist":"M"},{"brand_id":"0","name":"其他","frist":""}]
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
         * brand_id : 6
         * name : 飞利浦
         * frist : F
         */

        private String brand_id;
        private String name;
        private String frist;

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFrist() {
            return frist;
        }

        public void setFrist(String frist) {
            this.frist = frist;
        }
    }
}
