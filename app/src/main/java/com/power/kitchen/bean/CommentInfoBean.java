package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/7.
 */

public class CommentInfoBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : {"coment_id":"14","oid":"w20171025152927645828","m_u_id":"8","create_time":"1509524028","content":"\t\t\t\t\t\t\t\n\t\t\t\t\t\t","level":"1","status":"1"}
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
         * coment_id : 14
         * oid : w20171025152927645828
         * m_u_id : 8
         * create_time : 1509524028
         * content :

         * level : 1
         * status : 1
         */

        private String coment_id;
        private String oid;
        private String m_u_id;
        private String create_time;
        private String content;
        private String level;
        private String status;

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

        public String getM_u_id() {
            return m_u_id;
        }

        public void setM_u_id(String m_u_id) {
            this.m_u_id = m_u_id;
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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
