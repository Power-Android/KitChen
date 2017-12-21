package com.power.kitchen.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ruby on 2017/10/28.
 */

public class CommentListBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : {"count":"1","lists":[{"coment_id":"9","oid":"w20171017174947731607","level":"1","create_time":"1508234287","content":"\t\t\t\t\t\t\t\n\t\t\t\t\t\t","goods_code":"","goods_brand":"2","goods_brand_name":"美的","goods_type":"6","goods_type_name":"空调","goods_model":"","goods_is_warranty":"0","level_name":"满意"}],"p":"1"}
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
         * count : 1
         * lists : [{"coment_id":"9","oid":"w20171017174947731607","level":"1","create_time":"1508234287","content":"\t\t\t\t\t\t\t\n\t\t\t\t\t\t","goods_code":"","goods_brand":"2","goods_brand_name":"美的","goods_type":"6","goods_type_name":"空调","goods_model":"","goods_is_warranty":"0","level_name":"满意"}]
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
             * coment_id : 9
             * oid : w20171017174947731607
             * level : 1
             * create_time : 1508234287
             * content :

             * goods_code :
             * goods_brand : 2
             * goods_brand_name : 美的
             * goods_type : 6
             * goods_type_name : 空调
             * goods_model :
             * goods_is_warranty : 0
             * level_name : 满意
             */

            private String coment_id;
            private String oid;
            private String level;
            private String create_time;
            private String content;
            private String goods_code;
            private String goods_brand;
            private String goods_brand_name;
            private String goods_type;
            private String goods_type_name;
            private String goods_model;
            private String goods_is_warranty;
            private String level_name;

            public String getComent_id() {
                return coment_id;
            }

            public void setComent_id(String coment_id) {
                this.coment_id = coment_id;
            }

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
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

            public String getGoods_code() {
                return goods_code;
            }

            public void setGoods_code(String goods_code) {
                this.goods_code = goods_code;
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

            public String getGoods_is_warranty() {
                return goods_is_warranty;
            }

            public void setGoods_is_warranty(String goods_is_warranty) {
                this.goods_is_warranty = goods_is_warranty;
            }

            public String getLevel_name() {
                return level_name;
            }

            public void setLevel_name(String level_name) {
                this.level_name = level_name;
            }
        }
    }
}
