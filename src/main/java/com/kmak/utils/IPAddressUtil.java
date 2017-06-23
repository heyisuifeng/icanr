package com.kmak.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.CoreConnectionPNames;
//import org.apache.http.util.EntityUtils;

/**
 * 根据IP地址获取所在城市
 * 参考http://ip.taobao.com/index.php
 *
 */
@Slf4j
public final class IPAddressUtil {
    private static final String serviceURL = "http://ip.taobao.com/service/getIpInfo.php?";

    /**
     * 工具类无构造函数
     */
    private IPAddressUtil() {

    }

    /**
     * 获取本机IP地址
     *
     * @return
     */
    public static String getLocalAddressIP() {
        InetAddress local = null;

        try {
            local = InetAddress.getLocalHost();
        } catch (Exception e) {
            // 忽略即可
        }

        return (local == null) ? "127.0.0.1" : local.getHostAddress();
    }


    /**
     * 在使用Nginx代理时，request.getRemoteAddr()获取的IP实际上是代理服务器的地址，并不是客户端的IP地址
     * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，
     * 究竟哪个才是真正的用户端的真实IP呢？答案是取 X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 如： X-Forwarded-For：192.168.1.110， 192.168.1.120， 192.168.1.130， 192.168.1.100 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        String forwardedIPStr = request.getHeader("x-forwarded-for");

        if (StringUtils.hasText(forwardedIPStr) == false || "unknown".equalsIgnoreCase(forwardedIPStr) || "null".equalsIgnoreCase(forwardedIPStr)) {
            forwardedIPStr = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.hasText(forwardedIPStr) == false || "unknown".equalsIgnoreCase(forwardedIPStr) || "null".equalsIgnoreCase(forwardedIPStr)) {
            forwardedIPStr = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.hasText(forwardedIPStr) == false || "unknown".equalsIgnoreCase(forwardedIPStr) || "null".equalsIgnoreCase(forwardedIPStr)) {
            forwardedIPStr = request.getRemoteAddr();
        }

        String[] forwardedIPs = forwardedIPStr.split(",");
        for (String it : forwardedIPs) {
            if (it != null &&
                    ("unknown".equalsIgnoreCase(it) == false) &&
                    ("null".equalsIgnoreCase(it) == false)) {
                return it;
            }
        }

        return forwardedIPStr;
    }

    /**
     * 根据IP地址获取省市信息
     *
     * @param ip IP地址
     * @return 省市信息
     */
//    public static IPAddressResp getAddressByIP(String ip) {
//        IPAddressResp result = new IPAddressResp();
//
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        //
//        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
//        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
//
//        HttpGet httpget = new HttpGet(serviceURL + "ip=" + ip);
//
//        try {
//            HttpResponse response = httpClient.execute(httpget);
//
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                String jsonStr = EntityUtils.toString(entity);
//                result.convertFromString(jsonStr);
//            }
//        } catch (IOException e) {
//            log.error("getAddressByIP meet exception. ", e);
//        } finally {
//            httpget.abort();
//        }
//
//        return result;
//    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    if (inet != null) {
                        ipAddress = inet.getHostAddress();
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}

