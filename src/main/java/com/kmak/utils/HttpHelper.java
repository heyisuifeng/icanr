package com.kmak.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public class HttpHelper {
	
	/**
     * 发送post请求
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        try {
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(url);
            // 设置参数编码
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            if (params != null && params.size() > 0) {
                List<NameValuePair> parametersBodyList = new ArrayList<NameValuePair>();
                Set<Entry<String, Object>> entrySet = params.entrySet();
                Iterator<Entry<String, Object>> iter = entrySet.iterator();
                while (iter.hasNext()) {
                    Entry<String, Object> entry = iter.next();
                    if (entry.getKey() != null && entry.getValue() != null) {
                        parametersBodyList.add(new NameValuePair(entry.getKey(), entry.getValue().toString()));
                    }
                }
                log.debug("parametersBody:{}", parametersBodyList);
                method.setRequestBody(parametersBodyList.toArray(new NameValuePair[parametersBodyList.size()]));
            }
            int status = client.executeMethod(method);
            if (HttpStatus.SC_OK == status) {
                return method.getResponseBodyAsString();
            }
        } catch (HttpException e) {
        	log.error("发送post请求异常！"+e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
        	log.error("发送post请求异常！"+e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 发送post请求
     * @param url
     * @param headMap 请求头设置
     * @param params  请求参数
     * @return
     */
    public static String post(String url, Map<String,String> headMap, Map<String,String> params){
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
            PostMethod httpPost = new PostMethod(url);
            if (null != headMap){
                for (String key : headMap.keySet()){
                    httpPost.setRequestHeader(key,headMap.get(key));
                }
            }
            if (null != params){
                for (String pkey : params.keySet()) {
                    httpPost.addParameter(pkey,params.get(pkey));
                }
            }
            httpClient.executeMethod(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpPost.getResponseBodyAsStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null){
                stringBuffer.append(str);
            }
            reader.close();
            return stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * http post请求发送xml数据
     * @param url
     * @param xmlInfo
     * @return
     */
    public static String postHttpClient(String url, String xmlInfo){
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
            PostMethod httpPost = new PostMethod(url);
            httpPost.setRequestEntity(new StringRequestEntity(xmlInfo));
            httpClient.executeMethod(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpPost.getResponseBodyAsStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null){
                stringBuffer.append(str);
            }
            reader.close();
            return stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 需要加密执行，需要读取证书
     * @param url
     * @param xmlInfo
     * @param filePath
     * @param mrchId
     * @return
     * @throws Exception
     */
    public static String postHttpClientNeedSSL(String url, String xmlInfo,
                                               String filePath, String mrchId) throws Exception {
        //选择初始化密钥文件格式
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //得到密钥文件流
        FileInputStream inputStream = new FileInputStream(new File(filePath));
        try {
            //用商户的ID来解读文件
            keyStore.load(inputStream, mrchId.toCharArray());
        }finally {
            inputStream.close();
        }
        //用商户的ID来加载
        SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, mrchId.toCharArray()).build();
        //只支持TLS V1 协议
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] {"TLSv1"},
                null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        //用最新的httpclient加载密钥
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        StringBuffer ret = new StringBuffer();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(xmlInfo));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String str = "";
                    while ((str = reader.readLine()) != null){
                        ret.append(str);
                    }
                }
                EntityUtils.consume(entity);
            }finally {
                response.close();
            }
        }finally {
            httpClient.close();
        }
        return ret.toString();
    }

    public static String postHttps(String urlStr, String xmlInfo){
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:","no-cache");
            con.setRequestProperty("Cache-Control","no-cache");
            con.setRequestProperty("Content-Type","text/xml;charset=utf-8");
            OutputStreamWriter outWriter = new OutputStreamWriter(con.getOutputStream(),"utf-8");
            outWriter.write(xmlInfo);
            outWriter.flush();
            outWriter.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine())!=null){
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post请求并得到返回结果
     * @param requestUrl
     * @param requestMethod
     * @param output
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String output) {
        try{
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);
            if (null != output) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(output.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            connection.disconnect();
            return buffer.toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return "";
    }
}
