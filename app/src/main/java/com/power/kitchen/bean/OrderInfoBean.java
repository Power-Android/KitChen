package com.power.kitchen.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class OrderInfoBean implements Serializable {

    /**
     * status : 1
     * info : 订单查询成功！
     * data : {"info":{"oid":"w20171017174947731607","m_u_id":"8","m_d_id":"1","m_w_id":"1","price":"360.00","no_weixiu_info":"","status":"9","is_baojia":"1","wx_time":"1508234175","wx_images":"","wx_info":"http://shangchuuser.ip189.enet360.comhttp://shangchuuser.ip189.enet360.comhttp://shangchuuser.ip189.enet360.comhttp://shangchuuser.ip189.enet360.com","km":"1470.051","km_info":"","km_price":"300.00","wx_type":"0","type_info":"","type_price":"10.00","wx_peijian":"[]","peijian_price":"50.00","is_del":"1","status_accept":"1","status_pay":"0","status_comment":"1","create_time":"1508233787","accept_time":"1508233888","pay_time":"0","pay_type_name":"0","pay_type":"0","pay_json":"","comment_time":"1508234287","contact_name":"哦破horn","contact_mobile":"13800138000","contact_company":"来到了","contact_sheng_id":"14","contact_shi_id":"158","contact_qu_id":"1613","contact_address":"咳咳Pro","contact_juli":"1470051","contact_lat":"27.055897","contact_lng":"119.656277","goods_date":"2016-02-02","goods_code":"","goods_is_warranty":"0","goods_brand":"2","goods_brand_name":"美的","goods_type":"6","goods_type_name":"空调","goods_model":"","goods_images":"[\"http:\\/\\/shangchu.ip189.enet360.com\\/Uploads\\/order\\/20171017\\/m_u_8_1712021508231522666.jpeg\"]","goods_info":"[]","goods_describe":"考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461","quxiao_info":"","order_status_name":"已维修","order_accept_name":"已接单","order_pay_name":"未支付","order_comment_name":"已评论","contact_sheng_name":"福建省","contact_shi_name":"宁德市","contact_qu_name":"福安市"},"m_d_info":{"company_name":"联合代理公司","mobile":"13100000000"},"m_w_info":{"name":"测试","mobile":"13100000000"},"log":[{"id":"282","m_u_id":"8","m_d_id":"0","m_w_id":"0","info":"订单评论，等级：满意，内容:\t\t\t\t\t\t\t\n\t\t\t\t\t\t","create_time":"1508234287"},{"id":"281","m_u_id":"0","m_d_id":"0","m_w_id":"1","info":"师傅修改该订单为：已维修","create_time":"1508234207"},{"id":"280","m_u_id":"0","m_d_id":"0","m_w_id":"1","info":"师傅修改该订单报价为：360","create_time":"1508234175"},{"id":"279","m_u_id":"0","m_d_id":"0","m_w_id":"1","info":"师傅修改该订单报价为：230","create_time":"1508234117"},{"id":"277","m_u_id":"0","m_d_id":"0","m_w_id":"1","info":"师傅已经接收该订单！","create_time":"1508233888"},{"id":"276","m_u_id":"8","m_d_id":"0","m_w_id":"0","info":"订单创建成功，请等待维修师傅接单！","create_time":"1508233787"}],"comment":{"level":"1","content":"\t\t\t\t\t\t\t\n\t\t\t\t\t\t","level_name":"满意"}}
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
         * info : {"oid":"w20171017174947731607","m_u_id":"8","m_d_id":"1","m_w_id":"1","price":"360.00","no_weixiu_info":"","status":"9","is_baojia":"1","wx_time":"1508234175","wx_images":"","wx_info":"http://shangchuuser.ip189.enet360.comhttp://shangchuuser.ip189.enet360.comhttp://shangchuuser.ip189.enet360.comhttp://shangchuuser.ip189.enet360.com","km":"1470.051","km_info":"","km_price":"300.00","wx_type":"0","type_info":"","type_price":"10.00","wx_peijian":"[]","peijian_price":"50.00","is_del":"1","status_accept":"1","status_pay":"0","status_comment":"1","create_time":"1508233787","accept_time":"1508233888","pay_time":"0","pay_type_name":"0","pay_type":"0","pay_json":"","comment_time":"1508234287","contact_name":"哦破horn","contact_mobile":"13800138000","contact_company":"来到了","contact_sheng_id":"14","contact_shi_id":"158","contact_qu_id":"1613","contact_address":"咳咳Pro","contact_juli":"1470051","contact_lat":"27.055897","contact_lng":"119.656277","goods_date":"2016-02-02","goods_code":"","goods_is_warranty":"0","goods_brand":"2","goods_brand_name":"美的","goods_type":"6","goods_type_name":"空调","goods_model":"","goods_images":"[\"http:\\/\\/shangchu.ip189.enet360.com\\/Uploads\\/order\\/20171017\\/m_u_8_1712021508231522666.jpeg\"]","goods_info":"[]","goods_describe":"考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461","quxiao_info":"","order_status_name":"已维修","order_accept_name":"已接单","order_pay_name":"未支付","order_comment_name":"已评论","contact_sheng_name":"福建省","contact_shi_name":"宁德市","contact_qu_name":"福安市"}
         * m_d_info : {"company_name":"联合代理公司","mobile":"13100000000"}
         * m_w_info : {"name":"测试","mobile":"13100000000"}
         * log : [{"id":"282","m_u_id":"8","m_d_id":"0","m_w_id":"0","info":"订单评论，等级：满意，内容:\t\t\t\t\t\t\t\n\t\t\t\t\t\t","create_time":"1508234287"},{"id":"281","m_u_id":"0","m_d_id":"0","m_w_id":"1","info":"师傅修改该订单为：已维修","create_time":"1508234207"},{"id":"280","m_u_id":"0","m_d_id":"0","m_w_id":"1","info":"师傅修改该订单报价为：360","create_time":"1508234175"},{"id":"279","m_u_id":"0","m_d_id":"0","m_w_id":"1","info":"师傅修改该订单报价为：230","create_time":"1508234117"},{"id":"277","m_u_id":"0","m_d_id":"0","m_w_id":"1","info":"师傅已经接收该订单！","create_time":"1508233888"},{"id":"276","m_u_id":"8","m_d_id":"0","m_w_id":"0","info":"订单创建成功，请等待维修师傅接单！","create_time":"1508233787"}]
         * comment : {"level":"1","content":"\t\t\t\t\t\t\t\n\t\t\t\t\t\t","level_name":"满意"}
         */

        private InfoBean info;
        private MDInfoBean m_d_info;
        private MWInfoBean m_w_info;
        private CommentBean comment;
        private List<LogBean> log;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public MDInfoBean getM_d_info() {
            return m_d_info;
        }

        public void setM_d_info(MDInfoBean m_d_info) {
            this.m_d_info = m_d_info;
        }

        public MWInfoBean getM_w_info() {
            return m_w_info;
        }

        public void setM_w_info(MWInfoBean m_w_info) {
            this.m_w_info = m_w_info;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public List<LogBean> getLog() {
            return log;
        }

        public void setLog(List<LogBean> log) {
            this.log = log;
        }

        public static class InfoBean {
            /**
             * oid : w20171017174947731607
             * m_u_id : 8
             * m_d_id : 1
             * m_w_id : 1
             * price : 360.00
             * no_weixiu_info :
             * status : 9
             * is_baojia : 1
             * wx_time : 1508234175
             * wx_images :
             * wx_info : http://shangchuuser.ip189.enet360.comhttp://shangchuuser.ip189.enet360.comhttp://shangchuuser.ip189.enet360.comhttp://shangchuuser.ip189.enet360.com
             * km : 1470.051
             * km_info :
             * km_price : 300.00
             * wx_type : 0
             * type_info :
             * type_price : 10.00
             * wx_peijian : []
             * peijian_price : 50.00
             * is_del : 1
             * status_accept : 1
             * status_pay : 0
             * status_comment : 1
             * create_time : 1508233787
             * accept_time : 1508233888
             * pay_time : 0
             * pay_type_name : 0
             * pay_type : 0
             * pay_json :
             * comment_time : 1508234287
             * contact_name : 哦破horn
             * contact_mobile : 13800138000
             * contact_company : 来到了
             * contact_sheng_id : 14
             * contact_shi_id : 158
             * contact_qu_id : 1613
             * contact_address : 咳咳Pro
             * contact_juli : 1470051
             * contact_lat : 27.055897
             * contact_lng : 119.656277
             * goods_date : 2016-02-02
             * goods_code :
             * goods_is_warranty : 0
             * goods_brand : 2
             * goods_brand_name : 美的
             * goods_type : 6
             * goods_type_name : 空调
             * goods_model :
             * goods_images : ["http:\/\/shangchu.ip189.enet360.com\/Uploads\/order\/20171017\/m_u_8_1712021508231522666.jpeg"]
             * goods_info : []
             * goods_describe : 考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461考虑考虑啦咯啦咯啦咯啦849461
             * quxiao_info :
             * order_status_name : 已维修
             * order_accept_name : 已接单
             * order_pay_name : 未支付
             * order_comment_name : 已评论
             * contact_sheng_name : 福建省
             * contact_shi_name : 宁德市
             * contact_qu_name : 福安市
             */

            private String oid;
            private String m_u_id;
            private String m_d_id;
            private String m_w_id;
            private String price;
            private String no_weixiu_info;
            private String status;
            private String is_baojia;
            private String wx_time;
            private String wx_images;
            private String wx_info;
            private String km;
            private String km_info;
            private String km_price;
            private String wx_type;
            private String type_info;
            private String type_price;
            private String wx_peijian;
            private String peijian_price;
            private String is_del;
            private String status_accept;
            private String status_pay;
            private String status_comment;
            private String create_time;
            private String accept_time;
            private String pay_time;
            private String pay_type_name;
            private String pay_type;
            private String pay_json;
            private String comment_time;
            private String contact_name;
            private String contact_mobile;
            private String contact_company;
            private String contact_sheng_id;
            private String contact_shi_id;
            private String contact_qu_id;
            private String contact_address;
            private String contact_juli;
            private String contact_lat;
            private String contact_lng;
            private String goods_date;
            private String goods_code;
            private String goods_is_warranty;
            private String goods_brand;
            private String goods_brand_name;
            private String goods_type;
            private String goods_type_name;
            private String goods_model;
            private String goods_images;
            private String goods_info;
            private String goods_describe;
            private String quxiao_info;
            private String order_status_name;
            private String order_accept_name;
            private String order_pay_name;
            private String order_comment_name;
            private String contact_sheng_name;
            private String contact_shi_name;
            private String contact_qu_name;

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getM_u_id() {
                return m_u_id;
            }

            public void setM_u_id(String m_u_id) {
                this.m_u_id = m_u_id;
            }

            public String getM_d_id() {
                return m_d_id;
            }

            public void setM_d_id(String m_d_id) {
                this.m_d_id = m_d_id;
            }

            public String getM_w_id() {
                return m_w_id;
            }

            public void setM_w_id(String m_w_id) {
                this.m_w_id = m_w_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getNo_weixiu_info() {
                return no_weixiu_info;
            }

            public void setNo_weixiu_info(String no_weixiu_info) {
                this.no_weixiu_info = no_weixiu_info;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIs_baojia() {
                return is_baojia;
            }

            public void setIs_baojia(String is_baojia) {
                this.is_baojia = is_baojia;
            }

            public String getWx_time() {
                return wx_time;
            }

            public void setWx_time(String wx_time) {
                this.wx_time = wx_time;
            }

            public String getWx_images() {
                return wx_images;
            }

            public void setWx_images(String wx_images) {
                this.wx_images = wx_images;
            }

            public String getWx_info() {
                return wx_info;
            }

            public void setWx_info(String wx_info) {
                this.wx_info = wx_info;
            }

            public String getKm() {
                return km;
            }

            public void setKm(String km) {
                this.km = km;
            }

            public String getKm_info() {
                return km_info;
            }

            public void setKm_info(String km_info) {
                this.km_info = km_info;
            }

            public String getKm_price() {
                return km_price;
            }

            public void setKm_price(String km_price) {
                this.km_price = km_price;
            }

            public String getWx_type() {
                return wx_type;
            }

            public void setWx_type(String wx_type) {
                this.wx_type = wx_type;
            }

            public String getType_info() {
                return type_info;
            }

            public void setType_info(String type_info) {
                this.type_info = type_info;
            }

            public String getType_price() {
                return type_price;
            }

            public void setType_price(String type_price) {
                this.type_price = type_price;
            }

            public String getWx_peijian() {
                return wx_peijian;
            }

            public void setWx_peijian(String wx_peijian) {
                this.wx_peijian = wx_peijian;
            }

            public String getPeijian_price() {
                return peijian_price;
            }

            public void setPeijian_price(String peijian_price) {
                this.peijian_price = peijian_price;
            }

            public String getIs_del() {
                return is_del;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
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

            public String getAccept_time() {
                return accept_time;
            }

            public void setAccept_time(String accept_time) {
                this.accept_time = accept_time;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public String getPay_type_name() {
                return pay_type_name;
            }

            public void setPay_type_name(String pay_type_name) {
                this.pay_type_name = pay_type_name;
            }

            public String getPay_type() {
                return pay_type;
            }

            public void setPay_type(String pay_type) {
                this.pay_type = pay_type;
            }

            public String getPay_json() {
                return pay_json;
            }

            public void setPay_json(String pay_json) {
                this.pay_json = pay_json;
            }

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
            }

            public String getContact_name() {
                return contact_name;
            }

            public void setContact_name(String contact_name) {
                this.contact_name = contact_name;
            }

            public String getContact_mobile() {
                return contact_mobile;
            }

            public void setContact_mobile(String contact_mobile) {
                this.contact_mobile = contact_mobile;
            }

            public String getContact_company() {
                return contact_company;
            }

            public void setContact_company(String contact_company) {
                this.contact_company = contact_company;
            }

            public String getContact_sheng_id() {
                return contact_sheng_id;
            }

            public void setContact_sheng_id(String contact_sheng_id) {
                this.contact_sheng_id = contact_sheng_id;
            }

            public String getContact_shi_id() {
                return contact_shi_id;
            }

            public void setContact_shi_id(String contact_shi_id) {
                this.contact_shi_id = contact_shi_id;
            }

            public String getContact_qu_id() {
                return contact_qu_id;
            }

            public void setContact_qu_id(String contact_qu_id) {
                this.contact_qu_id = contact_qu_id;
            }

            public String getContact_address() {
                return contact_address;
            }

            public void setContact_address(String contact_address) {
                this.contact_address = contact_address;
            }

            public String getContact_juli() {
                return contact_juli;
            }

            public void setContact_juli(String contact_juli) {
                this.contact_juli = contact_juli;
            }

            public String getContact_lat() {
                return contact_lat;
            }

            public void setContact_lat(String contact_lat) {
                this.contact_lat = contact_lat;
            }

            public String getContact_lng() {
                return contact_lng;
            }

            public void setContact_lng(String contact_lng) {
                this.contact_lng = contact_lng;
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

            public String getGoods_type_name() {
                return goods_type_name;
            }

            public void setGoods_type_name(String goods_type_name) {
                this.goods_type_name = goods_type_name;
            }

            public String getGoods_model() {
                return goods_model;
            }

            public void setGoods_model(String goods_model) {
                this.goods_model = goods_model;
            }

            public String getGoods_images() {
                return goods_images;
            }

            public void setGoods_images(String goods_images) {
                this.goods_images = goods_images;
            }

            public String getGoods_info() {
                return goods_info;
            }

            public void setGoods_info(String goods_info) {
                this.goods_info = goods_info;
            }

            public String getGoods_describe() {
                return goods_describe;
            }

            public void setGoods_describe(String goods_describe) {
                this.goods_describe = goods_describe;
            }

            public String getQuxiao_info() {
                return quxiao_info;
            }

            public void setQuxiao_info(String quxiao_info) {
                this.quxiao_info = quxiao_info;
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

            public String getContact_sheng_name() {
                return contact_sheng_name;
            }

            public void setContact_sheng_name(String contact_sheng_name) {
                this.contact_sheng_name = contact_sheng_name;
            }

            public String getContact_shi_name() {
                return contact_shi_name;
            }

            public void setContact_shi_name(String contact_shi_name) {
                this.contact_shi_name = contact_shi_name;
            }

            public String getContact_qu_name() {
                return contact_qu_name;
            }

            public void setContact_qu_name(String contact_qu_name) {
                this.contact_qu_name = contact_qu_name;
            }
        }

        public static class MDInfoBean {
            /**
             * company_name : 联合代理公司
             * mobile : 13100000000
             */

            private String company_name;
            private String mobile;

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }

        public static class MWInfoBean {
            /**
             * name : 测试
             * mobile : 13100000000
             */

            private String name;
            private String mobile;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }

        public static class CommentBean {

            private String level;
            private String content;
            private String level_name;

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLevel_name() {
                return level_name;
            }

            public void setLevel_name(String level_name) {
                this.level_name = level_name;
            }
        }

        public static class LogBean {
            /**
             * id : 282
             * m_u_id : 8
             * m_d_id : 0
             * m_w_id : 0
             * info : 订单评论，等级：满意，内容:

             * create_time : 1508234287
             */

            private String id;
            private String m_u_id;
            private String m_d_id;
            private String m_w_id;
            private String info;
            private String create_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getM_u_id() {
                return m_u_id;
            }

            public void setM_u_id(String m_u_id) {
                this.m_u_id = m_u_id;
            }

            public String getM_d_id() {
                return m_d_id;
            }

            public void setM_d_id(String m_d_id) {
                this.m_d_id = m_d_id;
            }

            public String getM_w_id() {
                return m_w_id;
            }

            public void setM_w_id(String m_w_id) {
                this.m_w_id = m_w_id;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
