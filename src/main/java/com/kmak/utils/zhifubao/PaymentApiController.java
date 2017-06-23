package com.kmak.utils.zhifubao;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.kmak.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Controller
@RequestMapping("/api/payment")
public class PaymentApiController {
	
	/*private @Resource
    TserOrderMainService tserOrderMainService;
	private @Resource
    PaymentNotifyService paymentNotifyService;
	
	*//**
	 * 客户端发起支付宝支付
	 * @param orderId
	 * @return
	 *//*
	@RequestMapping(value = "/aliPay/{orderId}/{token}", method = RequestMethod.GET)
    public @ResponseBody
    RespMsgJson aliPay(@PathVariable long orderId, @PathVariable String token) {
		log.info("支付宝付款接收订单id==>" + orderId);
    	RespMsgJson rmj = new RespMsgJson();
        if (orderId <= 0) {
        	rmj.setStatus(ResultConst.INVALID_ARGUMENT);
        	rmj.setDesc("参数错误！");
            return rmj;
        }

        int checkResult = tserOrderMainService.isOkPay(orderId, token);
        if(checkResult == ResultConst.NOT_LOGIN) {
        	rmj.setStatus(ResultConst.NOT_LOGIN);
        	rmj.setDesc("用户未登录！");
            return rmj;
        }else if(checkResult == ResultConst.BAD_REQUEST) {
        	rmj.setStatus(ResultConst.BAD_REQUEST);
        	rmj.setDesc("非法请求！");
            return rmj;
        }else if(checkResult == ResultConst.ORDER_STATUS_ERROR) {
        	rmj.setStatus(ResultConst.ORDER_STATUS_ERROR);
        	rmj.setDesc("订单状态错误！");
            return rmj;
        }else if(checkResult == ResultConst.AMOUNT_CHECK_ERROR) {
        	rmj.setStatus(ResultConst.AMOUNT_CHECK_ERROR);
        	rmj.setDesc("订单金额错误！");
            return rmj;
        }
        
        OrderMainInfoJsonVo orderMain = tserOrderMainService.selectOrderMainByOrderId(orderId);
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.SERVER_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		if(orderMain.getSubCourseNameList() != null && orderMain.getSubCourseNameList().size() != 0) {
			model.setBody(orderMain.getSubCourseNameList().toString());
		}else {
			model.setBody(orderMain.getCourseName());
		}
		model.setSubject(orderMain.getCourseName());
		model.setOutTradeNo(CommonUtils.genTradeNoFromOrderId(Long.parseLong(orderMain.getId())));
		model.setTimeoutExpress("30m");
		model.setTotalAmount(orderMain.getAmount().toString());
		model.setProductCode("QUICK_MSECURITY_PAY");
		alipayRequest.setBizModel(model);
		alipayRequest.setNotifyUrl(SysConst.APIHTTPDEPLOY_BASIC_URL+AlipayConfig.NOTIFY_URL);
		try {
	        //这里和普通的接口调用不同，使用的是sdkExecute
	        AlipayTradeAppPayResponse aliPayresponse = alipayClient.sdkExecute(alipayRequest);
	        log.info("alipay param--》"+aliPayresponse.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
	        rmj.setStatus(ResultConst.OK);
	        rmj.setBody(aliPayresponse.getBody());
	    } catch (AlipayApiException e) {
		    e.printStackTrace();
		}
        return rmj;
    }
	
	*//**
	 * 查询订单状态接口
	 * @param orderId
	 * @return
	 *//*
	@RequestMapping(value = "/order/status/{orderId}", method = RequestMethod.GET)
    public @ResponseBody
    RespMsgJson getOrderStatus(@PathVariable long orderId) {
    	RespMsgJson rmj = new RespMsgJson();
        if (orderId <= 0) {
        	rmj.setStatus(ResultConst.INVALID_ARGUMENT);
        	rmj.setDesc("参数错误！");
            return rmj;
        }
        int result = tserOrderMainService.isPayedByOrderId(orderId);
        if(result == ResultConst.OK) {
        	rmj.setStatus(ResultConst.OK);
        	rmj.setDesc("已付款");
        }else {
        	rmj.setStatus(ResultConst.ERROR);
        	rmj.setDesc("未付款");
        }
        return rmj;
    }
	
	*//**
	 * 支付宝回调请求
	 * @param request
	 * @param response
	 * @throws IOException
	 *//*
	@RequestMapping(value = "/aliPay/notify")
    public void zhifubaoPayNotifyCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("支付宝支付回调请求");
        try {
        	//获取支付宝POST过来反馈信息
            AlipayNotifyParam alipayNotifyParam = new AlipayNotifyParam();
            if(alipayNotifyParam.fillString(request) != ResultConst.OK) {
            	log.info("支付宝支付回调请求校验失败");
            	response.getWriter().write("FAIL");
            }else {
            	AlipayNotifyLogicParam logicParam = new AlipayNotifyLogicParam();
                logicParam.setTrade_no(alipayNotifyParam.getTrade_no());
                logicParam.setOut_trade_no(alipayNotifyParam.getOut_trade_no());
                logicParam.setBuyer_id(alipayNotifyParam.getBuyer_id());
                logicParam.setSeller_id(alipayNotifyParam.getSeller_id());
                logicParam.setTotal_fee(alipayNotifyParam.getTotal_fee());
            	logicParam.setGmt_payment(alipayNotifyParam.getGmt_payment());
                if(paymentNotifyService.handleAlipayNotify(logicParam) == ResultConst.OK) {
                	response.getWriter().write("SUCCESS");
                }else {
                	response.getWriter().write("FAIL");
                }
            }
        }catch(Exception e) {
        	response.getWriter().write("FAIL");
        }
    }*/

}
