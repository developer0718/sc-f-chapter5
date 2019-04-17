package com.forezp.servicehi;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
public class ServiceHiApplication {

    public static void main(String[] args) {
        SpringApplication.run( ServiceHiApplication.class, args );
    }

    @Value("${server.port}")
    String port;

/*   @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name ) {
       System.out.println("成功");
        return "hi " + name + " ,i am from port:" + port;
    }*/
/*    @RequestMapping("/hi")
    public String home(@RequestBody User user,HttpServletRequest request) {
        System.out.println("request.getRequestURI():"+request.getRequestURI());
        System.out.println("request.getRequestURL():"+request.getRequestURL());

        System.out.println("成功");
        System.out.println("heheheheehehehe");
        System.out.println("sign:"+user);
        //System.out.println("body:"+body);
        return "dheheh";
    }*/

/*    @RequestMapping("/hi")
    public String home(@RequestBody User user,HttpServletRequest request) {
        System.out.println("request.getRequestURI():"+request.getRequestURI());
        System.out.println("request.getRequestURL():"+request.getRequestURL());
       String[] name = request.getParameterMap().get("name");
        String str= request.getQueryString();
        System.out.println("str:"+str);
        System.out.println("name:"+name[0]);
        System.out.println("heheheheehehehe");
        System.out.println("sign:"+user);
        //System.out.println("body:"+body);
        return "dheheh";
    }*/

/*
    @RequestMapping("/hi")
    public String home(@RequestBody String user,HttpServletRequest request) {
        System.out.println("request.getRequestURI():"+request.getRequestURI());
        System.out.println("request.getRequestURL():"+request.getRequestURL());
        String[] name = request.getParameterMap().get("name");
        String str= request.getQueryString();
        System.out.println("str:"+str);
        System.out.println("name:"+name[0]);
        System.out.println("sign:"+user);
        //System.out.println("body:"+body);
        return "dheheh";
    }
*/

    @RequestMapping(value="/hi",method = RequestMethod.POST)
    public String home(String userName,HttpServletRequest request) {
        System.out.println("request.getRequestURI():"+request.getRequestURI());
        System.out.println("request.getRequestURL():"+request.getRequestURL());
        String[] name = request.getParameterMap().get("name");
        String str= request.getQueryString();
        System.out.println("userName:"+userName);
        System.out.println("name:"+name[0]);
        //System.out.println("body:"+body);
        return "dheheh";
    }
}



