package com.kmak.utils.WeiXinUtils.entity;

import lombok.Data;

/**
 * Created by Leaf.Ye on 2017/4/24.
 */
@Data
public class WXPayNotifyLogicParam {
    private String transaction_id;//微信支付订单号:1217752501201407033233368018
    private String out_trade_no;//商户系统的订单号，与请求一致。交易单号：1212321211201407033568112322
    private int total_fee;//订单总金额，单位为分;1元=100分；
    private String openid;//用户在商户appid下的唯一标识
    private String mch_id;//微信支付分配的商户号
    private String time_end;//支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010

    private String appid;//公众账号ID
    private String nonce_str;//随机字符串，32位：数字+大写字母组合
    private String sign;//签名：回调的时候需要反校验
    private String result_code;//SUCCESS/FAIL
    private String trade_type;//交易类型:JSAPI、NATIVE、APP

    private String bank_type;//银行类型:ICBC_DEBIT:工商银行（借记卡）;ICBC_CREDIT:工商银行（信用卡）;
    private int cash_fee;//实际支付：现金支付金额订单现金支付金额

    private String return_code;//SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断

}
