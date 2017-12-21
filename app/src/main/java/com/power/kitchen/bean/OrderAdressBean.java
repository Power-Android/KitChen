package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/22.
 */

public class OrderAdressBean implements Serializable {
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
        private String name;
        private String company_name;
        private String tel;
        private String sheng_id;
        private String sheng_name;
        private String shi_id;
        private String shi_name;
        private String qu_id;
        private String qu_name;
        private String address;
        private String bd_lat;
        private String bd_lng;
        private String juli;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getSheng_id() {
            return sheng_id;
        }

        public void setSheng_id(String sheng_id) {
            this.sheng_id = sheng_id;
        }

        public String getSheng_name() {
            return sheng_name;
        }

        public void setSheng_name(String sheng_name) {
            this.sheng_name = sheng_name;
        }

        public String getShi_id() {
            return shi_id;
        }

        public void setShi_id(String shi_id) {
            this.shi_id = shi_id;
        }

        public String getShi_name() {
            return shi_name;
        }

        public void setShi_name(String shi_name) {
            this.shi_name = shi_name;
        }

        public String getQu_id() {
            return qu_id;
        }

        public void setQu_id(String qu_id) {
            this.qu_id = qu_id;
        }

        public String getQu_name() {
            return qu_name;
        }

        public void setQu_name(String qu_name) {
            this.qu_name = qu_name;
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

        public String getJuli() {
            return juli;
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }
    }
}
