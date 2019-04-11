package com.forezp.servicezuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
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

/*
      try {
          BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
          StringBuilder responseStrBuilder = new StringBuilder();
          String inputStr;
          while ((inputStr = streamReader.readLine()) != null){
              responseStrBuilder.append(inputStr);
          }


          JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
          jsonObject.put("test","test");
          String param= jsonObject.toJSONString();
          System.out.println(param);
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
*/





        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Map map = request.getParameterMap();
        for (Object key : map.keySet()) {
                System.out.println(key + ":" + map.get(key));
            }
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }
        log.info("ok");
        return null;
    }
}