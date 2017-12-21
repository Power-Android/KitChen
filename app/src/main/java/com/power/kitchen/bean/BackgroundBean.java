package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/11.
 */

public class BackgroundBean implements Serializable {

    /**
     * status : 1
     * info : 查询成功！
     * data : {"id":"16","name":"用户端首页背景","url":"http://www.enet360.com","image":"http://shangchu.ip189.enet360.com/Uploads/image/20171211/20171211150436_15259.jpg"}
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
         * id : 16
         * name : 用户端首页背景
         * url : http://www.enet360.com
         * image : http://shangchu.ip189.enet360.com/Uploads/image/20171211/20171211150436_15259.jpg
         */

        private String id;
        private String name;
        private String url;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
