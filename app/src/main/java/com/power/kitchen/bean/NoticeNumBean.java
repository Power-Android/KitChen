package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/11.
 */

public class NoticeNumBean implements Serializable {


    /**
     * status : 1
     * info : 数量为0
     * data : {"order_count":"14","system_count":"2","count":16}
     */

    private String status;
    private String info;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_count : 14
         * system_count : 2
         * count : 16
         */

        private String order_count;
        private String system_count;
        private String count;

        public String getOrder_count() {
            return order_count;
        }

        public void setOrder_count(String order_count) {
            this.order_count = order_count;
        }

        public String getSystem_count() {
            return system_count;
        }

        public void setSystem_count(String system_count) {
            this.system_count = system_count;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
