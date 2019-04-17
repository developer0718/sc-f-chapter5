package com.forezp.servicezuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.forezp.servicezuul.utils.AESUtil;
import com.forezp.servicezuul.utils.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-07-09
 **/
@Component
public class MyFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyFilter.class);
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

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

      try {
          //获取请求体中的数据
          BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
          StringBuilder responseStrBuilder = new StringBuilder();
          String inputStr;
          while ((inputStr = streamReader.readLine()) != null){
              responseStrBuilder.append(inputStr);
          }

          //将请求体中的数据解密
          JSONObject requestData = JSONObject.parseObject(responseStrBuilder.toString());
          String body = requestData.get("body").toString();
          JSONObject jsonBody = JSONObject.parseObject(AESUtil.Decrypt(body,"hehehehehehehehe",1));
          //取出自带签名
          String sign = requestData.get("sign").toString();
          log.info("请求自带签名sign："+sign);
          //将请求体中数据转换成map，并将参数拼接成目标格式字符串，以备生成签名
          Map mapBody = jsonBody;
          StringBuilder sb = new StringBuilder();
          String temp ="";
          //根据请求体数据，生成签名
          String secretkey="192006250b4c09247ec02edce69f6a2d";
          for(Object key : mapBody.keySet()){
              temp = mapBody.get(key).toString();
              if(!StringUtils.isBlank(temp)){
                  sb.append(key+"="+mapBody.get(key)+"&");
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
          if(!tempSign.equals(sign)) {
              log.warn("签名不匹配");
              ctx.setSendZuulResponse(false);
              ctx.setResponseStatusCode(401);
              try {
                  ctx.getResponse().setCharacterEncoding("utf-8");
                  ctx.getResponse().getWriter().write(new String("签名不匹配".getBytes("utf-8")));
              }catch (Exception e){
                  e.printStackTrace();
              }
              return null;
          }
          //重新生成一个请求，并将参数封装进请求
          String param= jsonBody.toJSONString();
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
      } catch (IOException e) {
          e.printStackTrace();
      }

        return null;

    }
}