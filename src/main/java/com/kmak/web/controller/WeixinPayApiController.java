package com.kmak.web.controller;

import com.kmak.utils.JdomParseXmlUtils;
import com.kmak.utils.WeiXinUtils.WeiChartUtil;
import com.kmak.utils.WeiXinUtils.entity.OrderQueryResult;
import com.kmak.utils.WeiXinUtils.entity.UnifiedorderResult;
import com.kmak.utils.WeiXinUtils.entity.WXPayResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付相关接口
 * 这里拷贝以前做过的项目的相关接口，部分地方要根据实际情况改成当前项目
 * Created by Leaf.Ye on 2017/4/14.
 */

@Slf4j
@Controller
@RequestMapping("/api/weixin/pay")
public class WeixinPayApiController {
    /**
     * 微信统一下单接口
     * @param orderId
     * @param token
     * @return
     */
    @RequestMapping(value = "/unifiedorder/{orderId}/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Map getUnifiedorderResult(@PathVariable long orderId, @PathVariable String token){
        log.info("微信APP支付统一下单接口开始，接收订单id==>" + orderId);
        Map map = new HashMap();
        if (orderId <= 0) {
            log.info("微信APP支付统一下单错误，订单ID错误：" + orderId);
            map.put("status","0000");
            map.put("desc","参数错误！");
            return map;
        }

        //参数校验，订单ID处理
        /*int checkResult = tserOrdemapainService.isOkPay(orderId, token);
        if (checkResult != ResultConst.OK){
            log.info("微信APP支付错误，原因参数非法");
            map.setStatus(checkResult);
            map.setDesc("参数非法！");
            return map;
        }
        OrdemapainInfoJsonVo ordemapain = tserOrdemapainService.selectOrdemapainByOrderId(orderId);*/
        try {
            //UnifiedorderResult unifiedorderResult = WeiChartUtil.getPreyId(CommonUtils.genTradeNoFromOrderId(Long.valueOf(ordemapain.getId())),ordemapain.getAmount().toString());
            UnifiedorderResult unifiedorderResult = WeiChartUtil.getPreyId("订单ID","订单金额");
            if (unifiedorderResult == null){
                log.info("微信统一下单接口未返回任何数据，获取预支付id失败");
                map.put("desc","请求连接正常，但第三方未返回任何数据");
            }
            //为android移动端生成签名和时间戳
            Map<String,String> androidSign = new HashMap<>();
            androidSign.put("appid", unifiedorderResult.getAppid());
            androidSign.put("noncestr", unifiedorderResult.getNonce_str());
            androidSign.put("package", "Sign=WXPay");
            androidSign.put("partnerid", unifiedorderResult.getMch_id());
            androidSign.put("prepayid", unifiedorderResult.getPrepay_id());
            String timestamp = ""+ System.currentTimeMillis()/1000;
            androidSign.put("timestamp", timestamp);
            unifiedorderResult.setAndroidSign(WeiChartUtil.getSign(androidSign));

            //为IOS移动端生成签名和时间戳
            Map<String,String> iosSign = new HashMap<>();
            iosSign.put("noncestr", unifiedorderResult.getNonce_str());
            iosSign.put("package", "Sign=WXPay");
            iosSign.put("partnerid", unifiedorderResult.getMch_id());
            iosSign.put("prepayid", unifiedorderResult.getPrepay_id());
            iosSign.put("timestamp", timestamp);
            unifiedorderResult.setIOSSign(WeiChartUtil.getSign(iosSign));

            unifiedorderResult.setTimestamp(timestamp);
            //状态码
            map.put("status","200");
            //结果数据
            map.put("body",unifiedorderResult);
        }catch (Exception e){
            log.info("微信APP支付统一下单发生异常，异常信息如下："+e.getLocalizedMessage());
            map.put("status","0001");
            map.put("desc","微信APP支付统一下单异常");
        }
        return map;
    }

    /**
     * 微信支付回调通知接口
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/notifyUrl")
    public String notifyWeixinPay(HttpServletRequest request) throws Exception {
        log.info("微信支付结果回调处理开始。。。");
        try {
            BufferedReader reader = request.getReader();
            String line = "";
            StringBuffer weixinPost = new StringBuffer();
            while ((line = reader.readLine())!=null){
                weixinPost.append(line);
            }
            if (reader!=null){
                reader.close();
            }
            //weixinPost.toString() 为[微信回调]接收到的报文
            if (weixinPost.toString()!=null && !"".equals(weixinPost.toString())){
                //将报文转换为map集合
                Map<String,String> wxPayResultMap = JdomParseXmlUtils.xmlString2Map(weixinPost.toString());

                if ("SUCCESS".equalsIgnoreCase(wxPayResultMap.get("return_code"))){
                    String retSing = wxPayResultMap.get("sign");
                    wxPayResultMap.remove("sign");

                    //反校验签名
                    String rightSign = WeiChartUtil.getSign(wxPayResultMap);
                    if (rightSign.equals(retSing)){
                        //todo 修改订单
                        WXPayResult wxPayResult = WeiChartUtil.getWXPayResult(wxPayResultMap);
                        //调用自己系统的相关业务处理，即这列表示已经支付成功了，自身系统需要做订单的相关处理
                        /*WXPayNotifyLogicParam param = new WXPayNotifyLogicParam();
                        BeanUtils.copyProperties(wxPayResult,param);
                        int result = paymentNotifyService.handleWeixinNotify(param);
                        if (result != ResultConst.OK){
                            log.info("微信支付回调失败，原因系统后台订单处理错误，错误状态码为：",result);
                            return "FAIL";
                        }*/
                    }else {
                        //签名失败
                        log.info("微信支付回调失败，原因：签名失败");
                        return "FAIL";
                    }
                }else {
                    //微信支付返回失败
                    log.info("微信支付返回失败，原因：",wxPayResultMap.get("return_msg"));
                    return "FAIL";
                }
            }else {
                //未获取到微信返回的结果
                log.info("微信支付回调失败，原因：未获取到微信返回的结果");
                return "FAIL";
            }
        }catch (Exception e){
            return "FAIL";
        }
        return "SUCCESS";
    }

    /**
     * 微信支付查询订单接口
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/orderquery/{orderId}/{token}",method = RequestMethod.GET)
    @ResponseBody
    public Map getWXOrderquery(@PathVariable long orderId, @PathVariable String token){
        Map map = new HashMap();
        /*if (orderId <= 0) {
            map.setStatus(ResultConst.INVALID_ARGUMENT);
            map.setDesc("参数错误！");
            return map;
        }
        int checkResult = tserOrdemapainService.isOkPay(orderId, token);
        if (checkResult != ResultConst.OK){
            map.setStatus(checkResult);
            return map;
        }*/
        OrderQueryResult queryResult = WeiChartUtil.getOrder(Long.toString(orderId));
        map.put("status",200);
        map.put("body",queryResult);
        return map;
    }
}
