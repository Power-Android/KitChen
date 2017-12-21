package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/3.
 */

public class SendSmsBean implements Serializable {

    /**
     * status : 1
     * info : 短信发生成功，请注意查收
     * data : {"code":114174}
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
         * code : 114174
         */

        private String code;
        private String src;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
