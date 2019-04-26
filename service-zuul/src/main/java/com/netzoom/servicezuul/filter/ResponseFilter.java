package com.netzoom.servicezuul.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.netzoom.servicezuul.utils.AESUtil;
import com.netzoom.servicezuul.utils.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.TreeMap;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

@Component
public class ResponseFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(RequestFilter.class);
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = getCurrentContext();
        HttpServletResponse response = context.getResponse();
        log.info("响应状态码response.Status："+response.getStatus());
        log.info("响应状态码response.getResponseStatusCode："+context.getResponseStatusCode());
        if(response.getStatus()==200){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object run() throws ZuulException {
        try {

            RequestContext context = getCurrentContext();
            InputStream stream = context.getResponseDataStream();
            String data= StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            log.info("responseBody:"+data);
            //context.setResponseBody("new responseBody: "+data);

            System.out.println("data:"+data);
            JSONObject reponseData = JSON.parseObject(data, Feature.OrderedField);
            //System.out.println(JSON.toJSONString(jsonObject));
            //JSONObject reponseData = JSONObject.parseObject(data);
            String secretBody = reponseData.getString("body");
            String header = reponseData.getString("header");
            JSONObject header1 = reponseData.getJSONObject("header");
            //将请求体中的数据解密
            String stringBody= AESUtil.Decrypt(secretBody,"96621bac8f5948fa97792138f635b49c",1);
            JSONObject body= JSONObject.parseObject(stringBody);
            //取出自带签名
            String sign = reponseData.getString("sign");
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
            log.info("请求体自带的签名tempSign:"+sign);
            //如果签名不匹配，结束过滤，并返回响应
            if(!tempSign.toUpperCase().equals(sign)) {
                log.warn("签名不匹配");
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(401);
                try {
                    JSONObject responseData = new JSONObject();
                    JSONObject responseBody = new JSONObject();
                    responseBody.put("success","false");
                    responseBody.put("code","100001");
                    responseBody.put("msg","响应体签名不匹配");
                    responseBody.put("data","{}");
                    responseData.put("body",responseBody);
                    context.setResponseBody(responseData.toJSONString());
              /*   context.getResponse().setCharacterEncoding("utf-8");
                 context.getResponse().getWriter().write(new String(responseData.toJSONString().getBytes("utf-8")));*/
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            System.out.println("body.toJSONString()"+body.toJSONString());
            context.setResponseBody(body.toJSONString());

            } catch (Exception e) {
                 e.printStackTrace();
             }




        return null;
    }
}
