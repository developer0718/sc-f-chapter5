package com.forezp.servicehi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

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

   /* @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name,@RequestBody String sign ) {
        return "hi " + name + " ,i am from port:" + port +sign;
    }*/
    @RequestMapping("/hi")
    public String home(@RequestBody String sign ) {
        return sign;
    }
}



