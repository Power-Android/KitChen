package com.power.kitchen.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class CitysBean implements IPickerViewData {

    /**
     * area_id : 2
     * name : 北京市
     * up_id : 1
     * son_lists : [{"area_id":"33","name":"市辖区","up_id":"2","son_lists":[{"area_id":"378","name":"东城区","up_id":"33"},{"area_id":"379","name":"西城区","up_id":"33"},{"area_id":"382","name":"朝阳区","up_id":"33"},{"area_id":"383","name":"丰台区","up_id":"33"},{"area_id":"384","name":"石景山区","up_id":"33"},{"area_id":"385","name":"海淀区","up_id":"33"},{"area_id":"386","name":"门头沟区","up_id":"33"},{"area_id":"387","name":"房山区","up_id":"33"},{"area_id":"388","name":"通州区","up_id":"33"},{"area_id":"389","name":"顺义区","up_id":"33"},{"area_id":"390","name":"昌平区","up_id":"33"},{"area_id":"391","name":"大兴区","up_id":"33"},{"area_id":"392","name":"怀柔区","up_id":"33"},{"area_id":"393","name":"平谷区","up_id":"33"}]}]
     */

    private String area_id;
    private String name;
    private String up_id;
    private List<SonListsBeanX> son_lists;

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

    public String getUp_id() {
        return up_id;
    }

    public void setUp_id(String up_id) {
        this.up_id = up_id;
    }

    public List<SonListsBeanX> getSon_lists() {
        return son_lists;
    }

    public void setSon_lists(List<SonListsBeanX> son_lists) {
        this.son_lists = son_lists;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }

    public static class SonListsBeanX {
        /**
         * area_id : 33
         * name : 市辖区
         * up_id : 2
         * son_lists : [{"area_id":"378","name":"东城区","up_id":"33"},{"area_id":"379","name":"西城区","up_id":"33"},{"area_id":"382","name":"朝阳区","up_id":"33"},{"area_id":"383","name":"丰台区","up_id":"33"},{"area_id":"384","name":"石景山区","up_id":"33"},{"area_id":"385","name":"海淀区","up_id":"33"},{"area_id":"386","name":"门头沟区","up_id":"33"},{"area_id":"387","name":"房山区","up_id":"33"},{"area_id":"388","name":"通州区","up_id":"33"},{"area_id":"389","name":"顺义区","up_id":"33"},{"area_id":"390","name":"昌平区","up_id":"33"},{"area_id":"391","name":"大兴区","up_id":"33"},{"area_id":"392","name":"怀柔区","up_id":"33"},{"area_id":"393","name":"平谷区","up_id":"33"}]
         */

        private String area_id;
        private String name;
        private String up_id;
        private List<SonListsBean> son_lists;

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

        public String getUp_id() {
            return up_id;
        }

        public void setUp_id(String up_id) {
            this.up_id = up_id;
        }

        public List<SonListsBean> getSon_lists() {
            return son_lists;
        }

        public void setSon_lists(List<SonListsBean> son_lists) {
            this.son_lists = son_lists;
        }

        public static class SonListsBean {
            /**
             * area_id : 378
             * name : 东城区
             * up_id : 33
             */

            private String area_id;
            private String name;
            private String up_id;

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

            public String getUp_id() {
                return up_id;
            }

            public void setUp_id(String up_id) {
                this.up_id = up_id;
            }
        }
    }
}
