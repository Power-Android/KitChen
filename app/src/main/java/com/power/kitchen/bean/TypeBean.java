package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/20.
 */

public class TypeBean implements Serializable {
    private String name;
    private String id;

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
}
