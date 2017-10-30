package com.power.kitchen.utils;

/**
 * Created by Administrator on 2017/10/25.
 */

public class Urls {

    static final String api_url = "http://shangchu.ip189.enet360.com";

    //获取Token
    public static final String get_token = api_url + "/Api/Oauth/get_token.html";
    //用户登录
    public static final String login = api_url + "/Api/User/login.html";
    //报修订单列表
    public static final String order_lists = api_url + "/Api/UserOrder/order_lists.html";
    //报修订单详情
    public static final String order_info = api_url + "/Api/UserOrder/order_info.html";
    //普通用户修改头像
    public static final String edit_face = api_url + "/Api/User/edit_face.html";
    //评价列表
    public static final String comment_lists = api_url + "/Api/UserComment/comment_lists.html";
}
