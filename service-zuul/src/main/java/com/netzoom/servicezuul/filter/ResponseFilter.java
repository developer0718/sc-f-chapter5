package com.netzoom.servicezuul.filter;


import com.alibaba.fastjson.JSONObject;
import com.netzoom.servicezuul.utils.AESUtil;
import com.netzoom.servicezuul.utils.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;


import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

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
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        try {

            RequestContext context = getCurrentContext();
            InputStream stream = context.getResponseDataStream();
            String data= StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            log.info("responseBody:"+data);
            //context.setResponseBody("new responseBody: "+data);
            //将请求体中的数据解密
            String hehe= AESUtil.Decrypt(data,"hehehehehehehehe",1);
            System.out.println("hehe:"+hehe);
            JSONObject reponseData = JSONObject.parseObject(hehe);
            JSONObject body = reponseData.getJSONObject("body");

            //取出自带签名
            String sign = reponseData.get("sign").toString();
            log.info("请求自带签名sign："+sign);
            //将请求体中数据转换成map，并将参数拼接成目标格式字符串，以备生成签名
            Map mapBody = body;
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

         /*   //Object zuulResponse = RequestContext.getCurrentContext().get("zuulResponse");
            InputStream stream = RequestContext.getCurrentContext().getResponseDataStream();
            if (stream != null) {
                //RibbonHttpResponse resp = (RibbonHttpResponse) zuulResponse;
                BufferedReader streamReader = new BufferedReader( new InputStreamReader(stream, "UTF-8"));
                StringBuilder requestStrBuilder = new StringBuilder();
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null){
                    requestStrBuilder.append(inputStr);
                }
                System.out.println("response:"+inputStr);
                //resp.close();*/
                //RequestContext.getCurrentContext().setResponseBody(inputStr);
            } catch (Exception e) {
            e.printStackTrace();
        }

/*        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response =ctx.getResponse();
        HttpResponse response111;

        ServletResponseWrapper responseWrapper=new ServletResponseWrapper(response);

        System.out.println("response:"+responseWrapper.getResponseBody());*/
  /*      try {
            //获取响应体中的数据
            BufferedWriter streamWriter = new BufferedWriter( new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            StringBuilder requestStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamWriter.readLine()) != null){
                requestStrBuilder.append(inputStr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
*/


        return null;
    }
}
