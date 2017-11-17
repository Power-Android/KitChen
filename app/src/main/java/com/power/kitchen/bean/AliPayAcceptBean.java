package com.power.kitchen.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/17.
 */

public class AliPayAcceptBean implements Serializable {

    /**
     * status : 1
     * info : ok
     * data : {"text":"alipay_sdk=alipay-sdk-php-20161101&app_id=2017101809369405&biz_content=%7B%22body%22%3A%22%E7%BB%B4%E4%BF%AE%E8%AE%A2%E5%8D%95%EF%BC%8C%E5%8D%95%E5%8F%B7%EF%BC%9Aw20171113154946468488%22%2C%22subject%22%3A%22APP+%E7%BB%B4%E4%BF%AE%E8%AE%A2%E5%8D%95%EF%BC%8C%E5%8D%95%E5%8F%B7%EF%BC%9Aw20171113154946468488%22%2C%22out_trade_no%22%3A%22w20171113154946468488%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22passback_params%22%3A%22weixiu%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fshangchu.ip189.enet360.com%2FApi%2FAppreturn%2Fnotify_alipay.html&sign_type=RSA2&timestamp=2017-11-17+09%3A48%3A51&version=1.0&sign=MojFyf7XnzN8%2BckUPN5n8PFxjsC2WX5NJ9YfBSAzMqQVxitHSD%2F7HJDvdOq87dzFg2aZwIS0hxTnhFSkkkAbD%2Bh21YxHYo%2B0%2FKQYeTRbwxFw4Npi2YS9tUdRuzlgn3h%2FsI1GPepSl%2FSxGoB1%2FJUeGbxKGardQ2rgycfSXaW37AqC6%2FqP0CKBLwOuq%2FI1CSaosaxBAG8hai0qPpUm0iKa0OlCrpZ4Awep37nQBPOLv7cOXtFiflidFQbxf34IAwFWYJVW2UvzR5YD60JQo5Ydlycc3G0GIJrXVnRpgU%2BbqyQ7XGDh9GvKCQQNulfwfjNebnNSc%2BdNZz1prDyu502wWQ%3D%3D","return_url":"http://shangchu.ip189.enet360.com/Api/Appreturn/notify_alipay.html"}
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
         * text : alipay_sdk=alipay-sdk-php-20161101&app_id=2017101809369405&biz_content=%7B%22body%22%3A%22%E7%BB%B4%E4%BF%AE%E8%AE%A2%E5%8D%95%EF%BC%8C%E5%8D%95%E5%8F%B7%EF%BC%9Aw20171113154946468488%22%2C%22subject%22%3A%22APP+%E7%BB%B4%E4%BF%AE%E8%AE%A2%E5%8D%95%EF%BC%8C%E5%8D%95%E5%8F%B7%EF%BC%9Aw20171113154946468488%22%2C%22out_trade_no%22%3A%22w20171113154946468488%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22passback_params%22%3A%22weixiu%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fshangchu.ip189.enet360.com%2FApi%2FAppreturn%2Fnotify_alipay.html&sign_type=RSA2&timestamp=2017-11-17+09%3A48%3A51&version=1.0&sign=MojFyf7XnzN8%2BckUPN5n8PFxjsC2WX5NJ9YfBSAzMqQVxitHSD%2F7HJDvdOq87dzFg2aZwIS0hxTnhFSkkkAbD%2Bh21YxHYo%2B0%2FKQYeTRbwxFw4Npi2YS9tUdRuzlgn3h%2FsI1GPepSl%2FSxGoB1%2FJUeGbxKGardQ2rgycfSXaW37AqC6%2FqP0CKBLwOuq%2FI1CSaosaxBAG8hai0qPpUm0iKa0OlCrpZ4Awep37nQBPOLv7cOXtFiflidFQbxf34IAwFWYJVW2UvzR5YD60JQo5Ydlycc3G0GIJrXVnRpgU%2BbqyQ7XGDh9GvKCQQNulfwfjNebnNSc%2BdNZz1prDyu502wWQ%3D%3D
         * return_url : http://shangchu.ip189.enet360.com/Api/Appreturn/notify_alipay.html
         */

        private String text;
        private String return_url;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getReturn_url() {
            return return_url;
        }

        public void setReturn_url(String return_url) {
            this.return_url = return_url;
        }
    }
}
