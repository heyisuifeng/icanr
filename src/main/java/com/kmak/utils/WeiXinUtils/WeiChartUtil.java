package com.kmak.utils.WeiXinUtils;

import com.kmak.utils.HashCoderUtil;
import com.kmak.utils.HttpHelper;
import com.kmak.utils.IPAddressUtil;
import com.kmak.utils.JdomParseXmlUtils;
import com.kmak.utils.WeiXinUtils.entity.OrderQueryResult;
import com.kmak.utils.WeiXinUtils.entity.UnifiedorderResult;
import com.kmak.utils.WeiXinUtils.entity.WXPayResult;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Leaf.Ye on 2017/4/12.
 */
public class WeiChartUtil {
    /**
     * 得到微信预付单的返回ID
     * @param orderId  商户自己的订单号
     * @param totalFee  总金额  （分）
     * @return
     */
    public static UnifiedorderResult getPreyId(String orderId, String totalFee){
        Map<String,String> reqMap = new HashMap<>();
        reqMap.put("appid", WeiChartConfig.AppId);
        reqMap.put("mch_id",WeiChartConfig.MchId);
        reqMap.put("nonce_str",getRandomString(32));//获取32位的随机数
        reqMap.put("body",WeiChartConfig.body);
        reqMap.put("out_trade_no",orderId);
        reqMap.put("total_fee",changeToFen(totalFee));
        reqMap.put("spbill_create_ip", IPAddressUtil.getLocalAddressIP());
        //加上自己的域名
        reqMap.put("notify_url", "网站域名"+WeiChartConfig.notify_url);
        reqMap.put("trade_type","APP");
        String sign = getSign(reqMap);
        reqMap.put("sign",sign);

        String reqStr = createXml(reqMap);
        String responseXml = HttpHelper.httpsRequest(WeiChartConfig.PrepayUrl,"POST",reqStr);
        Map<String,String> resultMap = JdomParseXmlUtils.xmlString2Map(responseXml);
        return getUnifiedorderResult(resultMap);
    }

    /**
     * 查询订单
     * @param orderId 商户自己的订单号
     * @return
     */
    public static OrderQueryResult getOrder(String orderId){
        Map<String,String> reqMap = new HashMap<>();
        reqMap.put("appid", WeiChartConfig.AppId);
        reqMap.put("mch_id",WeiChartConfig.MchId);
        reqMap.put("nonce_str",getRandomString(32));
        reqMap.put("out_trade_no",orderId);
        reqMap.put("sign",getSign(reqMap));
        String reqStr = createXml(reqMap);
        String resStr = HttpHelper.postHttpClient(WeiChartConfig.OrderUrl,reqStr);
        Map<String,String> resultMap = JdomParseXmlUtils.xmlString2Map(resStr);
        return getQrderQueryResult(resultMap);
    }

    /**
     * 查询订单微信返回
     * @param map
     * @return
     */
    public static OrderQueryResult getQrderQueryResult(Map<String,String> map){
        if (CollectionUtils.isEmpty(map)){
            return null;
        }
        OrderQueryResult result = new OrderQueryResult();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if("return_code".equals(entry.getKey())){
                result.setReturn_code(entry.getValue());
            }

            if("return_msg".equals(entry.getKey())){
                result.setReturn_msg(entry.getValue());
            }

            if ("appid".equals(entry.getKey())){
                result.setAppid(entry.getValue());
            }

            if("mch_id".equals(entry.getKey())){
                result.setMch_id(entry.getValue());
            }

            if("nonce_str".equals(entry.getKey())){
                result.setNonce_str(entry.getValue());
            }

            if("sign".equals(entry.getKey())){
                result.setSign(entry.getValue());
            }

            if("result_code".equals(entry.getKey())){
                result.setResult_code(entry.getValue());
            }

            if ("openid".equals(entry.getKey())){
                result.setOpenid(entry.getValue());
            }

            if ("trade_type".equals(entry.getKey())){
                result.setTrade_type(entry.getValue());
            }

            if ("trade_state".equals(entry.getKey())){
                result.setTrade_state(entry.getValue());
            }

            if ("bank_type".equals(entry.getKey())){
                result.setBank_type(entry.getValue());
            }

            if ("total_fee".equals(entry.getKey())){
                result.setTotal_fee(entry.getValue());
            }

            if ("cash_fee".equals(entry.getKey())){
                result.setCash_fee(entry.getValue());
            }

            if ("transaction_id".equals(entry.getKey())){
                result.setTransaction_id(entry.getValue());
            }

            if ("out_trade_no".equals(entry.getKey())){
                result.setOut_trade_no(entry.getValue());
            }

            if ("time_end".equals(entry.getKey())){
                result.setTime_end(entry.getValue());
            }

            if ("trade_state_desc".equals(entry.getKey())){
                result.setTrade_state_desc(entry.getValue());
            }
        }
        return result;
    }

    /**
     * 统一下单获取微信返回
     * @param map
     * @return
     */
    public static UnifiedorderResult getUnifiedorderResult(Map<String,String> map){
        if (CollectionUtils.isEmpty(map)){
            return null;
        }
        UnifiedorderResult result = new UnifiedorderResult();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if("return_code".equals(entry.getKey())){
                result.setReturn_code(entry.getValue());
            }

            if("return_msg".equals(entry.getKey())){
                result.setReturn_msg(entry.getValue());
            }

            if("appid".equals(entry.getKey())){
                result.setAppid(entry.getValue());
            }

            if("mch_id".equals(entry.getKey())){
                result.setMch_id(entry.getValue());
            }

            if("nonce_str".equals(entry.getKey())){
                result.setNonce_str(entry.getValue());
            }

            if("sign".equals(entry.getKey())){
                result.setSign(entry.getValue());
            }

            if("result_code".equals(entry.getKey())){
                result.setResult_code(entry.getValue());
            }

            if("prepay_id".equals(entry.getKey())){
                result.setPrepay_id(entry.getValue());
            }

            if("trade_type".equals(entry.getKey())){
                result.setTrade_type(entry.getValue());
            }
        }
        return result;
    }

    /**
     * 构建微信支付结果返回的参数
     * @param map
     * @return
     */
    public static WXPayResult getWXPayResult(Map<String,String> map){
        if (CollectionUtils.isEmpty(map)){
            return null;
        }
        WXPayResult result = new WXPayResult();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if("return_code".equals(entry.getKey())){
                result.setReturn_code(entry.getValue());
            }
            if ("return_msg".equals(entry.getKey())){
                result.setReturn_msg(entry.getValue());
            }
            if("appid".equals(entry.getKey())){
                result.setAppid(entry.getValue());
            }
            if("mch_id".equals(entry.getKey())){
                result.setMch_id(entry.getValue());
            }
            if("nonce_str".equals(entry.getKey())){
                result.setNonce_str(entry.getValue());
            }
            if("sign".equals(entry.getKey())){
                result.setSign(entry.getValue());
            }
            if("result_code".equals(entry.getKey())){
                result.setResult_code(entry.getValue());
            }
            if("openid".equals(entry.getKey())){
                result.setOpenid(entry.getValue());
            }
            if ("trade_type".equals(entry.getKey())){
                result.setTrade_type(entry.getValue());
            }
            if ("bank_type".equals(entry.getKey())){
                result.setBank_type(entry.getValue());
            }
            if ("total_fee".equals(entry.getKey())){
                result.setTotal_fee(Integer.valueOf(entry.getValue()));
            }
            if ("cash_fee".equals(entry.getKey())){
                result.setCash_fee(Integer.valueOf(entry.getValue()));
            }
            if ("transaction_id".equals(entry.getKey())){
                result.setTransaction_id(entry.getValue());
            }
            if ("out_trade_no".equals(entry.getKey())){
                result.setOut_trade_no(entry.getValue());
            }
            if ("time_end".equals(entry.getKey())){
                result.setTime_end(entry.getValue());
            }
        }
        return result;
    }

    /**
     * 生成length位的随机字符串
     * @return
     */
    public static String getRandomString(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<length;i++){
            stringBuffer.append(str.charAt(random.nextInt(62)));
        }
        return stringBuffer.toString();
    }

    /**
     * 构造最终发送的数据
     * 传入map  生成头为XML的xml字符串，例：<xml><key>123</key></xml>
     * @param map
     * @return
     */
    public static String createXml(Map<String,String> map){
        if (CollectionUtils.isEmpty(map)){
            return "";
        }
        String[] keys = map.keySet().toArray(new String[0]);
        StringBuffer stringBuffer = new StringBuffer("<xml>");
        for (String key : keys) {
            String value = map.get(key);
            if (value !=null && !"".equals(value)){
                stringBuffer.append("<").append(key).append("><![CDATA[").append(value).append("]]></").append(key).append(">");
            }
        }
        stringBuffer.append("</xml>");
        return stringBuffer.toString();
    }

    /**
     * 构造微信接口所需的sign字段
     * @param map
     * @return
     */
    public static String getSign(Map<String, String> map){
        String[] keys = map.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuffer stringBuffer = new StringBuffer();
        for (String key : keys) {
            String value = map.get(key);
            if (value != null && !"".equals(value)){
                stringBuffer.append(key).append("=").append(value).append("&");
            }
        }
        stringBuffer.append("key=").append(WeiChartConfig.AppSercret);

        System.out.println(stringBuffer.toString());
        //MD5加密
        return HashCoderUtil.MD5Encode(stringBuffer.toString(),"UTF-8").toUpperCase();
    }

    /**
     * 将金额转换成分
     * @param yuan 元格式的
     * @return 分
     */
    public static String changeToFen(String yuan){
        String total_fen = "";
        double p = Double.parseDouble(yuan);
        int totalFee = (int)(p*100);
        total_fen = Integer.toString(totalFee);
        return total_fen;
    }
}
