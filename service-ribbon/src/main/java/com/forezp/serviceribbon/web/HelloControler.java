package com.forezp.serviceribbon.web;

import com.forezp.serviceribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-07-09
 **/
@RestController
public class HelloControler {

    @Autowired
    HelloService helloService;

    //@RequestMapping (value = "/hi")
    //@RequestMapping (value = "/api/AdminCompany/GetAllCompany")
     @RequestMapping (value = "/hi")
    public String hi(@RequestBody String name,HttpServletRequest request) {

       /* System.out.println("request:"+request.getParameterMap().get("name"));
        String[] s = request.getParameterMap().get("name");
        String my= s[0];
        System.out.println(my);*/
       System.out.println("request.getRequestURI():"+request.getRequestURI());
       System.out.println("request.getRequestURL():"+request.getRequestURL());

        System.out.println("dddd============");
        return helloService.hiService( name );
    }
}
