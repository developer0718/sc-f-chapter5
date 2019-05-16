package com.netzoom.servicezuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.netzoom.servicezuul.utils.AESUtil;
import com.netzoom.servicezuul.utils.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author liuzw
 **/
@Component
public class RequestFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(RequestFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
//        try {
//            RequestContext ctx = RequestContext.getCurrentContext();
//            HttpServletRequest request = ctx.getRequest();
//            //获取请求体中的数据
//            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
//            StringBuilder requestStrBuilder = new StringBuilder();
//            String inputStr;
//            while ((inputStr = streamReader.readLine()) != null){
//                requestStrBuilder.append(inputStr);
//            }
//            //将请求体中的数据解密
//            JSONObject requestData = JSONObject.parseObject(requestStrBuilder.toString());
//            JSONObject header= requestData.getJSONObject("header");
//            String serviceId= header.getString("serviceId");
//            String sign = header.getString("sign");
//
//            HttpPost httpPost = new HttpPost("http://127.0.0.1:2222/login");
//            httpPost.setHeader("content-type",MediaType.APPLICATION_JSON_UTF8_VALUE);
//
//            // 创建Httpclient对象
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//
//            //另一种不会报错的创建方式
//            //CloseableHttpClient httpClient = HttpClients.custom().build();
//            CloseableHttpResponse response = null;
//            JSONObject signData = new JSONObject();
//            log.info("服务登录：serviceId:"+serviceId+",sign:"+sign);
//            signData.put("serviceId",serviceId);
//            signData.put("sign",sign);
//
//
//            //设置post的内容
//            StringEntity entity = new StringEntity(signData.toString(),ContentType.APPLICATION_JSON);
//            //entity.setContentEncoding("UTF-8");
//            httpPost.setEntity(entity);
//
//            //执行执行http请求
//            response = httpClient.execute(httpPost);
//            //得到Response结果
//            if(response.getStatusLine().getStatusCode() == 200){
//                String resultString = EntityUtils.toString(response.getEntity(), "utf-8");
//                log.info("返回结果：" + resultString);
//                JSONObject loginResult = JSONObject.parseObject(resultString);
//                String result = loginResult.getString("result");
//                if("success".equals(result)){
//                    flag.set("success");
//                    return true;
//                }else{
//                    flag.set("fail");
//                    return true;
//                }
//            }else{
//                flag.set("fail");
//                return true;
//            }
//
//        }catch (Exception e){
//            flag.set("fail");
//            e.printStackTrace();
//            log.info("hahahahahaha");
//            return true;
//        }
//    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
            try {
                //获取请求体中的数据
                BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
                StringBuilder requestStrBuilder = new StringBuilder();
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null){
                    requestStrBuilder.append(inputStr);
                }

                //将请求体中的数据解密
                JSONObject requestData = JSON.parseObject(requestStrBuilder.toString(), Feature.OrderedField);
                log.info("requestStrBuilder.toString():"+requestStrBuilder.toString());
                String secretBody = requestData.getString("body");
                String header = requestData.getString("header");
                log.info("secretBody"+secretBody);
                log.info("header"+header);
                //将请求体中的数据解密
                String stringBody= AESUtil.Decrypt(secretBody,"96621bac8f5948fa97792138f635b49c",1);
                log.info("stringBody"+stringBody);
                JSONObject body= JSONObject.parseObject(stringBody);
                //取出自带签名
                String sign = requestData.getString("sign");
                log.info("请求自带签名sign："+sign);
                //将请求体中数据转换成map，并将参数拼接成目标格式字符串，以备生成签名
                log.info("bodytoJSONString"+body.toJSONString());
                TreeMap<String,String> hashMap = new TreeMap();
                hashMap.put("header",header);
                hashMap.put("body",stringBody);
                StringBuilder sb = new StringBuilder();
                String temp ="";
                //根据请求体数据，生成签名
                String secretkey="c70815ad156d4ddb9839bc8af1b7b6f6";
                for(Object key : hashMap.keySet()){
                    temp = hashMap.get(key).toString();
                    if(!StringUtils.isBlank(temp)){
                        sb.append(key+"="+hashMap.get(key)+"&");
                    }
                }
                //将目标字符串拼接秘钥
                sb.append("secretkey="+secretkey);
                log.info("MD5加密前生成签名目标字符串："+sb);
                //将目标字符串生成签名
                String tempSign = EncryptUtil.md5Encode(sb.toString());
                log.info("MD5加密后生成签名结果："+tempSign);
                log.info("MD5加密后生成签名大写结果："+tempSign.toUpperCase());
                log.info("请求体自带的签名tempSign:"+sign);
                //如果签名不匹配，结束过滤，并返回响应
               if(!tempSign.toUpperCase().equals(sign)) {
                    log.warn("签名不匹配");
                    ctx.setSendZuulResponse(false);
                    //ctx.setResponseStatusCode(200);
                    ctx.set("msg","请求体签名不匹配");
                    return null;
                }
                //重新生成一个请求，并将参数封装进请求
                String param= stringBody;
                log.info("传给服务提供方参数："+param);
                final byte[] reqBodyBytes = param.getBytes();
                ctx.setRequest(new HttpServletRequestWrapper(request){
                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(reqBodyBytes);
                    }
                    @Override
                    public int getContentLength() {
                        return reqBodyBytes.length;
                    }
                    @Override
                    public long getContentLengthLong() {
                        return reqBodyBytes.length;
                    }
                });
            } catch (Exception e) {
                ctx.set("msg","网关处理请求信息异常");
                ctx.setSendZuulResponse(false);
                ctx.set("msg","网关处理请求信息异常");
                e.printStackTrace();
            }
            return null;

        }



}