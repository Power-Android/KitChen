package com.power.kitchen.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class AreaListBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : {"count":"3","lists":[{"area_id":"32","name":"有你们","tel":"15666666666","sheng_id":"10","shi_id":"108","qu_id":"1178","address":"诺还丑诺","bd_lat":"31.633565","bd_lng":"121.535398","is_def":"0","company_name":"TOTO肉灭口需要7k7k","sheng_name":"上海市","shi_name":"县","qu_name":"崇明县"},{"area_id":"31","name":"无证之罪","tel":"13111111111","sheng_id":"2","shi_id":"33","qu_id":"386","address":"欧诺婆婆拍侧漏运气","bd_lat":"40.000893","bd_lng":"115.795795","is_def":"0","company_name":"ISO头绪WPS彼此斧头欧诺","sheng_name":"北京市","shi_name":"市辖区","qu_name":"门头沟区"},{"area_id":"30","name":"aaaaaaas","tel":"13800138000","sheng_id":"14","shi_id":"158","qu_id":"1611","address":"哈哈群头诺","bd_lat":"27.094313","bd_lng":"119.313326","is_def":"0","company_name":"bbbbbbbbb","sheng_name":"福建省","shi_name":"宁德市","qu_name":"周宁县"}],"p":"1"}
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
         * lists : [{"area_id":"32","name":"有你们","tel":"15666666666","sheng_id":"10","shi_id":"108","qu_id":"1178","address":"诺还丑诺","bd_lat":"31.633565","bd_lng":"121.535398","is_def":"0","company_name":"TOTO肉灭口需要7k7k","sheng_name":"上海市","shi_name":"县","qu_name":"崇明县"},{"area_id":"31","name":"无证之罪","tel":"13111111111","sheng_id":"2","shi_id":"33","qu_id":"386","address":"欧诺婆婆拍侧漏运气","bd_lat":"40.000893","bd_lng":"115.795795","is_def":"0","company_name":"ISO头绪WPS彼此斧头欧诺","sheng_name":"北京市","shi_name":"市辖区","qu_name":"门头沟区"},{"area_id":"30","name":"aaaaaaas","tel":"13800138000","sheng_id":"14","shi_id":"158","qu_id":"1611","address":"哈哈群头诺","bd_lat":"27.094313","bd_lng":"119.313326","is_def":"0","company_name":"bbbbbbbbb","sheng_name":"福建省","shi_name":"宁德市","qu_name":"周宁县"}]
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
             * area_id : 32
             * name : 有你们
             * tel : 15666666666
             * sheng_id : 10
             * shi_id : 108
             * qu_id : 1178
             * address : 诺还丑诺
             * bd_lat : 31.633565
             * bd_lng : 121.535398
             * is_def : 0
             * company_name : TOTO肉灭口需要7k7k
             * sheng_name : 上海市
             * shi_name : 县
             * qu_name : 崇明县
             */

            private String area_id;
            private String name;
            private String tel;
            private String sheng_id;
            private String shi_id;
            private String qu_id;
            private String address;
            private String bd_lat;
            private String bd_lng;
            private String is_def;
            private String company_name;
            private String sheng_name;
            private String shi_name;
            private String qu_name;

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getIs_def() {
                return is_def;
            }

            public void setIs_def(String is_def) {
                this.is_def = is_def;
            }

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
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
}
