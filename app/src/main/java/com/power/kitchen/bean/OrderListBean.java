package com.power.kitchen.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class OrderListBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : {"count":"3","lists":[{"oid":"w20171025153045798668","price":"0.00","status":"1","status_accept":"1","status_pay":"0","status_comment":"0","create_time":"1508916645","goods_date":"2019-04-05","goods_code":"53956986","goods_is_warranty":"0","goods_brand":"2","goods_brand_name":"美的","goods_type":"4","goods_model":"","goods_describe":"不晓得","order_status_name":"待维修","order_accept_name":"已接单","order_pay_name":"未支付","order_comment_name":"未评论","goods_type_name":"洗衣机"},{"oid":"w20171025105741521686","price":"0.00","status":"1","status_accept":"1","status_pay":"0","status_comment":"0","create_time":"1508900261","goods_date":"2016-02-09","goods_code":"这是一条新订单","goods_is_warranty":"1","goods_brand":"1","goods_brand_name":"海尔","goods_type":"6","goods_model":"这是一条新订单","goods_describe":"1吧把家里1吧把家里1吧11吧把家里吧把家1吧把家里1吧把家里1吧11吧把家里吧把家里把家里1吧把家里里把家里1吧把家里","order_status_name":"待维修","order_accept_name":"已接单","order_pay_name":"未支付","order_comment_name":"未评论","goods_type_name":"空调"},{"oid":"w20171024103900171501","price":"0.00","status":"1","status_accept":"1","status_pay":"0","status_comment":"0","create_time":"1508812740","goods_date":"2016-02-02","goods_code":"集体牛过会","goods_is_warranty":"1","goods_brand":"2","goods_brand_name":"美的","goods_type":"4","goods_model":"","goods_describe":"","order_status_name":"待维修","order_accept_name":"已接单","order_pay_name":"未支付","order_comment_name":"未评论","goods_type_name":"洗衣机"}],"p":"1"}
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
         * count : 3
         * lists : [{"oid":"w20171025153045798668","price":"0.00","status":"1","status_accept":"1","status_pay":"0","status_comment":"0","create_time":"1508916645","goods_date":"2019-04-05","goods_code":"53956986","goods_is_warranty":"0","goods_brand":"2","goods_brand_name":"美的","goods_type":"4","goods_model":"","goods_describe":"不晓得","order_status_name":"待维修","order_accept_name":"已接单","order_pay_name":"未支付","order_comment_name":"未评论","goods_type_name":"洗衣机"},{"oid":"w20171025105741521686","price":"0.00","status":"1","status_accept":"1","status_pay":"0","status_comment":"0","create_time":"1508900261","goods_date":"2016-02-09","goods_code":"这是一条新订单","goods_is_warranty":"1","goods_brand":"1","goods_brand_name":"海尔","goods_type":"6","goods_model":"这是一条新订单","goods_describe":"1吧把家里1吧把家里1吧11吧把家里吧把家1吧把家里1吧把家里1吧11吧把家里吧把家里把家里1吧把家里里把家里1吧把家里","order_status_name":"待维修","order_accept_name":"已接单","order_pay_name":"未支付","order_comment_name":"未评论","goods_type_name":"空调"},{"oid":"w20171024103900171501","price":"0.00","status":"1","status_accept":"1","status_pay":"0","status_comment":"0","create_time":"1508812740","goods_date":"2016-02-02","goods_code":"集体牛过会","goods_is_warranty":"1","goods_brand":"2","goods_brand_name":"美的","goods_type":"4","goods_model":"","goods_describe":"","order_status_name":"待维修","order_accept_name":"已接单","order_pay_name":"未支付","order_comment_name":"未评论","goods_type_name":"洗衣机"}]
         * p : 1
         */

        private String count;
        private String p;
        private List<ListsBean> lists;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * oid : w20171025153045798668
             * price : 0.00
             * status : 1
             * status_accept : 1
             * status_pay : 0
             * status_comment : 0
             * create_time : 1508916645
             * goods_date : 2019-04-05
             * goods_code : 53956986
             * goods_is_warranty : 0
             * goods_brand : 2
             * goods_brand_name : 美的
             * goods_type : 4
             * goods_model :
             * goods_describe : 不晓得
             * order_status_name : 待维修
             * order_accept_name : 已接单
             * order_pay_name : 未支付
             * order_comment_name : 未评论
             * goods_type_name : 洗衣机
             */

            private String oid;
            private String price;
            private String status;
            private String status_accept;
            private String status_pay;
            private String status_comment;
            private String create_time;
            private String goods_date;
            private String goods_code;
            private String goods_is_warranty;
            private String goods_brand;
            private String goods_brand_name;
            private String goods_type;
            private String goods_model;
            private String goods_describe;
            private String order_status_name;
            private String order_accept_name;
            private String order_pay_name;
            private String order_comment_name;
            private String goods_type_name;

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatus_accept() {
                return status_accept;
            }

            public void setStatus_accept(String status_accept) {
                this.status_accept = status_accept;
            }

            public String getStatus_pay() {
                return status_pay;
            }

            public void setStatus_pay(String status_pay) {
                this.status_pay = status_pay;
            }

            public String getStatus_comment() {
                return status_comment;
            }

            public void setStatus_comment(String status_comment) {
                this.status_comment = status_comment;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getGoods_date() {
                return goods_date;
            }

            public void setGoods_date(String goods_date) {
                this.goods_date = goods_date;
            }

            public String getGoods_code() {
                return goods_code;
            }

            public void setGoods_code(String goods_code) {
                this.goods_code = goods_code;
            }

            public String getGoods_is_warranty() {
                return goods_is_warranty;
            }

            public void setGoods_is_warranty(String goods_is_warranty) {
                this.goods_is_warranty = goods_is_warranty;
            }

            public String getGoods_brand() {
                return goods_brand;
            }

            public void setGoods_brand(String goods_brand) {
                this.goods_brand = goods_brand;
            }

            public String getGoods_brand_name() {
                return goods_brand_name;
            }

            public void setGoods_brand_name(String goods_brand_name) {
                this.goods_brand_name = goods_brand_name;
            }

            public String getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(String goods_type) {
                this.goods_type = goods_type;
            }

            public String getGoods_model() {
                return goods_model;
            }

            public void setGoods_model(String goods_model) {
                this.goods_model = goods_model;
            }

            public String getGoods_describe() {
                return goods_describe;
            }

            public void setGoods_describe(String goods_describe) {
                this.goods_describe = goods_describe;
            }

            public String getOrder_status_name() {
                return order_status_name;
            }

            public void setOrder_status_name(String order_status_name) {
                this.order_status_name = order_status_name;
            }

            public String getOrder_accept_name() {
                return order_accept_name;
            }

            public void setOrder_accept_name(String order_accept_name) {
                this.order_accept_name = order_accept_name;
            }

            public String getOrder_pay_name() {
                return order_pay_name;
            }

            public void setOrder_pay_name(String order_pay_name) {
                this.order_pay_name = order_pay_name;
            }

            public String getOrder_comment_name() {
                return order_comment_name;
            }

            public void setOrder_comment_name(String order_comment_name) {
                this.order_comment_name = order_comment_name;
            }

            public String getGoods_type_name() {
                return goods_type_name;
            }

            public void setGoods_type_name(String goods_type_name) {
                this.goods_type_name = goods_type_name;
            }
        }
    }
}
