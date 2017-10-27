package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class TokenBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : {"token":"5d6867894d3e85acc1bbf06edb89ea73","time":86355}
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
         * token : 5d6867894d3e85acc1bbf06edb89ea73
         * time : 86355
         */

        private String token;
        private int time;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
