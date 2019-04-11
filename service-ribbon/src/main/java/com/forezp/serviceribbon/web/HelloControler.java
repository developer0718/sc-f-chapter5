package com.forezp.serviceribbon.web;

import com.forezp.serviceribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping (value = "/hi")
    public String hi(@RequestParam String name,@RequestBody String sign ) {
        System.out.println("dddd============"+sign);
        return helloService.hiService( name );
    }
}
