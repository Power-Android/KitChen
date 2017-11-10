package com.power.kitchen.bean;

/**
 * Created by Administrator on 2017/11/7.
 */

public class UpLoadImgBean {

    /**
     * status : 1
     * info : ok
     * data : {"src":"http://www.enet360.com/aaaa.png"}
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
         * src : http://www.enet360.com/aaaa.png
         */

        private String src;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
