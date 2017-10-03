package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 */

public class WaiteRepairBean implements Serializable {
    private String bianhao;
    private String is_jiedan;
    private String name;
    private String leixing;
    private String is_baoxiu;
    private String xinghao;
    private String shijian;

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getIs_jiedan() {
        return is_jiedan;
    }

    public void setIs_jiedan(String is_jiedan) {
        this.is_jiedan = is_jiedan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getIs_baoxiu() {
        return is_baoxiu;
    }

    public void setIs_baoxiu(String is_baoxiu) {
        this.is_baoxiu = is_baoxiu;
    }

    public String getXinghao() {
        return xinghao;
    }

    public void setXinghao(String xinghao) {
        this.xinghao = xinghao;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }
}
