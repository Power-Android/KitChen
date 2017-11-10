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
    //普通用户注册
    public static final String register = api_url + "/Api/User/register.html";
    //用户协议
    public static final String user_xieyi = api_url + "/Api/Oauth/user_xieyi.html";
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
    //查询评价详情
    public static final String comment_info = api_url + "/Api/UserComment/comment_info.html";
    //普通用户查询个人信息
    public static final String user_info = api_url + "/Api/User/user_info.html";
    //普通用户修改个人资料
    public static final String edit_info = api_url + "/Api/User/edit_info.html";
    //用户地址列表
    public static final String area_list = api_url + "/Api/UserAdd/area_lists.html";
    //添加用户地址
    public static final String area_add = api_url + "/Api/UserAdd/area_add.html";
    //修改用户地址
    public static final String area_edit = api_url + "/Api/UserAdd/area_edit.html";
    //删除用户地址
    public static final String area_del = api_url + "/Api/UserAdd/area_del.html";
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
    //公司简介
    public static final String about_us = api_url + "/Api/Oauth/about.html";
    //用户取消订单原因列表
    public static final String quxiao_tips = api_url + "/Api/Oauth/quxiao_tips.html";
    //报修订单取消
    public static final String order_close = api_url + "/Api/UserOrder/order_close.html";
    //发布报修订单
    public static final String order_add = api_url + "/Api/UserOrder/order_add.html";
    //报修上传图片接口
    public static final String uploads_img = api_url + "/Api/Oauth/uploads_img.html";
    //省市区地区子查询接口
    public static final String area_lists = api_url + "/Api/Oauth/area_lists.html";
    //所有地区
    public static final String area_all_lists = api_url + "/Api/Oauth/area_all_lists.html";
    //普通用户修改密码
    public static final String edit_password = api_url + "/Api/User/edit_password.html";
    //普通用户找回密码
    public static final String resetpass = api_url + "/Api/User/resetpass.html";
    //获取手机验证码
    public static final String send_sms = api_url + "/Api/Oauth/send_sms.html";
    //图形验证码
    public static final String verify_img = api_url + "/Api/Oauth/verify_img.html";
}
