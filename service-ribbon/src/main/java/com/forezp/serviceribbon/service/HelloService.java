package com.forezp.serviceribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-07-09
 **/
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private HttpServletRequest request; //自动注入request

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name){

        System.out.println("name:"+name);
        System.out.println("request:"+request);
       return restTemplate.postForObject("http://SERVICE-HI/hi?name="+name,"ddd",String.class);
       //return restTemplate.postForObject("http://SERVICE-HI/hi?name="+name,request,String.class);
       //return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }

    public String hiError(String name) {
        System.out.println("呵呵呵");
        return "hi,"+name+",sorry,error!";
        //return new ResponseEntity<String>();
    }

}
