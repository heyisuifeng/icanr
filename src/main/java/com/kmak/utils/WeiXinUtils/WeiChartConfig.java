package com.kmak.utils.WeiXinUtils;


/**
 * 基础信息配置
 * Created by Leaf.Ye on 2017/4/11.
 */
public class WeiChartConfig {

    /**
     * 预支付请求地址
     */
    public static final String PrepayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 查询订单地址
     */
    public static final String OrderUrl = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 关闭订单地址
     */
    public static final String CloseOrderUrl = "https://api.mch.weixin.qq.com/pay/closeorder";

    /**
     * 申请退款地址
     */
    public static final String RefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 查询退款地址
     */
    public static final String RefundQueryUrl = "https://api.mch.weixin.qq.com/pay/refundquery";

    /**
     * 下载账单地址
     */
    public static final String DownloadBillUrl = "https://api.mch.weixin.qq.com/pay/downloadbill";

    /**
     * 商户APPID
     */
    public static final String AppId = "";

    /**
     * 商户号 获取支付能力后，从邮件中得到
     */
    public static final String MchId = "";

    /**
     * 商户秘钥  32位，在微信商户平台中设置
     * key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
     */
    public static final String AppSercret = "";

    /**
     * 服务器异步通知页面路径
     */
    public static String notify_url = "";

    /**
     * 商品描述
     */
    public static String body = "";

}
