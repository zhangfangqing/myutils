package com.fangqing.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangqing.exception.VccpaySystemException;

/**
 * @功能   httpclient工具类
 *
 * @author zhangfangqing 
 * @date 2016年8月2日 
 * @time 下午2:18:42
 */
public class HttpClientUtil {
    private final Logger                 logger                = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final int             timeout               = 30000;

    /**
     * 每个主机最大连接数
     */
    private static final int             maxConnectionsPerHost = 20;
    /**
     * 最大总共连接数
     */
    private static final int             maxTotalConnection    = 100;

    private static final String          encoding              = "UTF-8";

    public static final String           RETURN_CODE_FAIL      = "request_fail";

    private static HttpConnectionManager httpConnectionManager = null;

    public HttpClientUtil() {
        if (httpConnectionManager == null) {
            initConnectionManager();
        }
    }

    private void initConnectionManager() {
        httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(timeout);
        params.setSoTimeout(timeout);
        params.setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
        params.setMaxTotalConnections(maxTotalConnection);

    }

    /**
     * post请求
     * 
     * @param url 请求地址
     * @param params 请求参数
     * @return 返回结果
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public String postRequest(String url, HashMap<String, String> params) {
        String result = "";
        PostMethod postMethod = null;

        try {
            //创建连接
            if (httpConnectionManager == null) {
                initConnectionManager();
            }
            HttpClient httpClient = null;
            httpClient = new HttpClient(httpConnectionManager);
            // 设置编码
            httpClient.getParams().setContentCharset(encoding);
            httpClient.getParams().setHttpElementCharset(encoding);
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

            postMethod = new PostMethod(url);

            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            NameValuePair nameValuePair = null;
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                nameValuePair = new NameValuePair(key, val);
                list.add(nameValuePair);
            }
            NameValuePair[] data = list.toArray(new NameValuePair[list.size()]);
            postMethod.setRequestBody(data);

            //请求
            httpClient.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            logger.error("=================单向发卡方提交支付请求,httpclient异常：", e);
            result = RETURN_CODE_FAIL;
        } finally {
            if (postMethod != null) {
                //释放连接
                postMethod.releaseConnection();
            }
        }
        return result;

    }

    /**
     * post请求-没有参数名，只有参数值
     * 
     * @param url 请求地址
     * @param body 请求参数
     * @return 返回结果
     * @throws Exception
     */
    public String postRequestOfNoParamName(String url, String body) {
        String result = "";
        PostMethod postMethod = null;

        try {
            //创建连接
            if (httpConnectionManager == null) {
                initConnectionManager();
            }
            HttpClient httpClient = null;
            httpClient = new HttpClient(httpConnectionManager);
            // 设置编码
            httpClient.getParams().setContentCharset(encoding);
            httpClient.getParams().setHttpElementCharset(encoding);
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

            postMethod = new PostMethod(url);
            postMethod.setRequestEntity(new StringRequestEntity(body, "", encoding));

            //请求
            httpClient.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            logger.error("=================单向发卡方提交支付请求,httpclient异常：", e);
            result = RETURN_CODE_FAIL;
        } finally {
            if (postMethod != null) {
                //释放连接
                postMethod.releaseConnection();
            }
        }
        return result;

    }

    public String getRequest(String url) {
        String result = "";
        GetMethod getMethod = null;
        try {
            //创建连接
            if (httpConnectionManager == null) {
                initConnectionManager();
            }
            HttpClient httpClient = null;
            httpClient = new HttpClient(httpConnectionManager);
            // 设置编码
            httpClient.getParams().setContentCharset(encoding);
            httpClient.getParams().setHttpElementCharset(encoding);
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

            getMethod = new GetMethod(url);
            //请求
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                result = getMethod.getResponseBodyAsString();
            } else {
                result = RETURN_CODE_FAIL;
            }
        } catch (Exception e) {
            //          result = RETURN_CODE_FAIL;
            result = null;
            throw new VccpaySystemException(e.getMessage(), e);
        } finally {
            if (getMethod != null) {
                //释放连接
                getMethod.releaseConnection();
            }
        }
        return result;

    }

}
