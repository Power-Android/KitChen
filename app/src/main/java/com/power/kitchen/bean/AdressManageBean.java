package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/23.
 */

public class AdressManageBean implements Serializable {
    private String telephone;
    private String name;
    private String address;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
