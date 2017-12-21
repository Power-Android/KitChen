package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/30.
 */

public class UserInfoBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : {"id":"2c76iPDX3FiclGKJfh6CBMuL2eCKBDqn4rvOxI/c","mobile":"18515885055","true_name":"乐克乐克","sheng_id":"2","face":"http://shangchu.ip189.enet360.com/Uploads/face/face_8.png","shi_id":"33","qu_id":"378","address":"","bd_lat":"","bd_lng":"","sheng_name":"北京市","shi_name":"市辖区","qu_name":"东城区"}
     */

    private int status;
    private String info;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
         * id : 2c76iPDX3FiclGKJfh6CBMuL2eCKBDqn4rvOxI/c
         * mobile : 18515885055
         * true_name : 乐克乐克
         * sheng_id : 2
         * face : http://shangchu.ip189.enet360.com/Uploads/face/face_8.png
         * shi_id : 33
         * qu_id : 378
         * address :
         * bd_lat :
         * bd_lng :
         * sheng_name : 北京市
         * shi_name : 市辖区
         * qu_name : 东城区
         */

        private String id;
        private String mobile;
        private String true_name;
        private String sheng_id;
        private String face;
        private String shi_id;
        private String qu_id;
        private String address;
        private String bd_lat;
        private String bd_lng;
        private String sheng_name;
        private String shi_name;
        private String qu_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTrue_name() {
            return true_name;
        }

        public void setTrue_name(String true_name) {
            this.true_name = true_name;
        }

        public String getSheng_id() {
            return sheng_id;
        }

        public void setSheng_id(String sheng_id) {
            this.sheng_id = sheng_id;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getShi_id() {
            return shi_id;
        }

        public void setShi_id(String shi_id) {
            this.shi_id = shi_id;
        }

        public String getQu_id() {
            return qu_id;
        }

        public void setQu_id(String qu_id) {
            this.qu_id = qu_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBd_lat() {
            return bd_lat;
        }

        public void setBd_lat(String bd_lat) {
            this.bd_lat = bd_lat;
        }

        public String getBd_lng() {
            return bd_lng;
        }

        public void setBd_lng(String bd_lng) {
            this.bd_lng = bd_lng;
        }

        public String getSheng_name() {
            return sheng_name;
        }

        public void setSheng_name(String sheng_name) {
            this.sheng_name = sheng_name;
        }

        public String getShi_name() {
            return shi_name;
        }

        public void setShi_name(String shi_name) {
            this.shi_name = shi_name;
        }

        public String getQu_name() {
            return qu_name;
        }

        public void setQu_name(String qu_name) {
            this.qu_name = qu_name;
        }
    }
}
