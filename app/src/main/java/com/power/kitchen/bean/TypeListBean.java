package com.power.kitchen.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class TypeListBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : [{"type_id":"2","brand_id":"4","name":"空调"},{"type_id":"6","brand_id":"1","name":"空调"},{"type_id":"5","brand_id":"1","name":"冰箱"},{"type_id":"4","brand_id":"2","name":"洗衣机"},{"type_id":"3","brand_id":"4","name":"平板电视"},{"type_id":"0","brand_id":"0","name":"其他"}]
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
         * type_id : 2
         * brand_id : 4
         * name : 空调
         */

        private String type_id;
        private String brand_id;
        private String name;

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

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
    }
}
