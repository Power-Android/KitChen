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
    //维修评价选项
    public static final String weixiu_comment_type = api_url + "/Api/Oauth/weixiu_comment_type.html";
    //发布评价
    public static final String comment_add = api_url + "/Api/UserComment/comment_add.html";
    //评价列表
    public static final String comment_lists = api_url + "/Api/UserComment/comment_lists.html";
    //普通用户查询个人信息
    public static final String user_info = api_url + "/Api/User/user_info.html";
    //普通用户修改个人资料
    public static final String edit_info = api_url + "/Api/User/edit_info.html";
    //用户地址列表
    public static final String area_list = api_url + "/Api/UserAdd/area_lists.html";
    //添加用户地址
    public static final String area_add = api_url + "/Api/UserAdd/area_add.html";
    //订单通知
    public static final String notice_order_list = api_url + "/Api/UserNotice/notice_order_lists.html";
    //订单通知 详情
    public static final String notice_order_info = api_url + "/Api/UserNotice/notice_order_info.html";
    //系统通知
    public static final String notice_system_list = api_url + "/Api/UserNotice/notice_system_lists.html";
    //系统通知 详情
    public static final String notice_system_info = api_url + "/Api/UserNotice/notice_system_info.html";
    //报修设备品牌
    public static final String brand_list = api_url + "/Api/Oauth/brands_lists.html";
    //报修设备类型
    public static final String type_list = api_url + "/Api/Oauth/types_lists.html";
}
