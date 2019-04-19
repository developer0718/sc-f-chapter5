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
        //System.out.println("request:"+request.toString());
        //System.out.println("request:"+request.getRequestURI());
        //System.out.println("request:"+request.getParameterMap());
       return restTemplate.postForObject("http://SERVICE-HI/hi?name="+name,"ddd",String.class);
       //return restTemplate.postForObject("http://SERVICE-HI/hi?name="+name,request,String.class);
       //return restTemplate.getForObject("http://ACCOUNTMSSERVICE/api/AdminCompany/GetAllCompany",String.class);
        //return restTemplate.postForObject("http://ACCOUNTMSSERVICE/api/AdminCompany/GetAllCompany?name="+name,"ddd",String.class);
    }

    public String hiError(String name) {
        System.out.println("呵呵呵");
        //return "hi,"+name+",sorry,error!";
        //return new ResponseEntity<String>();
        return "rYUqsRFW0XxsM8p2/4P5LSi6A+XSAe8RMYHowrOgXUflC5rIrj0m0zvAjWVBrGBIIOla/smWSkB0tD0qqlMLJRcxXjvZFGz5qmKeq/7JauhnJPIRqON+R4mujaDJEQAjIvst3mg8WfPY/JrYRuL927kiHcuf+wSNWMnW8+0JEIn7/AOmJWTB8quqQYs++JRaL88AqmN7wJleT0QRjubWJti1gwbCo14U3LM2h6eRdICrMCY0d6upkaHJvUKkPI1lQ8N/S7Isun02QHFZ1s0S09bQ9fIYLfzZyi7Zv16iIUyVzw4PPuzm2yHyaLtwSRrJF8kMZNGTUR/zL6Y+q7C0y74Pkpu+Rwy3FTXFbwap4bnT6nfZcNd2ROHzjrEL189r";  }

}
