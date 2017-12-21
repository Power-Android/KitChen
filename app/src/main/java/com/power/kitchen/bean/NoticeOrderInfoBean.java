package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/1.
 */

public class NoticeOrderInfoBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : {"notice_id":"47","title":"您维修的订单：w20171101115356748289已经被接单","info":"","status":"0","create_time":"1509508457","content":"您维修的订单：w20171101115356748289已经被接单，师傅姓名：测试电话：13100000000"}
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
         * notice_id : 47
         * title : 您维修的订单：w20171101115356748289已经被接单
         * info :
         * status : 0
         * create_time : 1509508457
         * content : 您维修的订单：w20171101115356748289已经被接单，师傅姓名：测试电话：13100000000
         */

        private String notice_id;
        private String title;
        private String info;
        private String status;
        private String create_time;
        private String content;

        public String getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(String notice_id) {
            this.notice_id = notice_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
