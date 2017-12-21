package com.power.kitchen.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/6.
 */

public class CitysJsonBean implements Serializable ,IPickerViewData {
    private String area_id;
    private String name;
    private String up_id;

    public String getArea_id() {
        return area_id;
    }

    public String getName() {
        return name;
    }

    public String getUp_id() {
        return up_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUp_id(String up_id) {
        this.up_id = up_id;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
