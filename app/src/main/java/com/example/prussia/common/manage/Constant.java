package com.example.prussia.common.manage;

/**
 * Created by Administrator on 2016/9/huise 0001.
 */
public final class Constant {
    /**
     * 测试环境
     */
    public static final String URL = "http://miaomiao-api.emibuy.com/";//网络连接域名(测试域名)
    /**
     * APP状态常量
     */
    public static final String SOURCE = "android";//网络请求来源
    public static final String HTTP_RANKINGLIST = "http://h5.gamemm.com/ranking/ranking_pass?user_id="; //排行榜
    public static final int GIFTTYPE = 520;//区分大礼物和小礼物值
    public static final String GIFTURL = "http://static.gamemm.com/images/gift/";//礼物连接
    public static final String AESCOD = "123456";//AES加密钥匙

    /**
     * HTTP网络请求相关常量
     */
    public static final class HttpCode {
        public static final String HTTP_SEVICE_ERROR = "服务器开小差了，请稍后再试吧~";
        public static final String HTTP_NETWORK_ERROR = "呀，网络出了问题    请检查网络连接";
        public static final int HTTP_DIALOG_NULL = 0x000000;//不需要dialog
        public static final int HTTP_DIALOG_ORIGINAL = 0x000001;//内置dialog
        public static final int HTTP_DIALOG_REFRESH = 0x000002;//刷新dialog
        public static final int HTTP_DIALOG_LOADMORE = 0x000003;//加载dialog
    }

    /**
     * 支付方式常量
     */
    public static final class PayCode {
        public static final String PLAY_WEIXIN = "weixin";//支付方式（微信支付）
        public static final String PLAY_ZHIFUBAO = "alipay";//支付方式（支付宝支付）
        public static final String PLAY_YUE = "balance";//支付方式（余额支付）
    }

    /**
     * 偏好设置常量
     */
    public static final class SpCode {
        public static final String SP_USERINFO = "sp_userinfo";//偏好设置存用户信息
        public static final String SP_PUBLIC = "sp_public";//偏好设置存公共信息
    }

    /**
     * 推送常量
     */
    public static final class PushCode {

    }

    /**
     * 验证码常量
     */
    public static final class EnCode {
        //是否验证手机已注册1-验证0-不验证（1、3、5 传1）
        public static final String TYPE_REGCODE_BINDING = "5";//微信绑定获取验证码
        public static final String TYPE_REGCODE_BINDING_QQ = "6";//qq登录获取验证码参数
        public static final String ISCHECK_REGCODE_BINDING = "1";//已注册1
    }

    /**
     * EventBus事件常量
     */
    public static final class EventCode {
        public static final int Activity_UserSetting_Name = 0x100001;
    }

    /**
     * activity界面回传常量
     * (注意返回码值不能太大)
     */
    public static final class AFRCode {
        public static final int RESULTCODE_ZHEKOU_MIAOMIAO_ORDER_ACTIVITY = 1001;// 折扣券返回喵喵下单的返回码
    }
}