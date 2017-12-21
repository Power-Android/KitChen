package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/23.
 */

public class MessageBean implements Serializable {
    private String content;
    private String time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
