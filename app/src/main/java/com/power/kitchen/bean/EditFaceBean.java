package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by ruby on 2017/10/28.
 */

public class EditFaceBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : {"face":"http://www.enet360.com/Uploads/face/face_1.png"}
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
         * face : http://www.enet360.com/Uploads/face/face_1.png
         */

        private String face;

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
