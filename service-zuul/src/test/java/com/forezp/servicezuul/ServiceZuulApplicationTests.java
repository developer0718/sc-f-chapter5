package com.forezp.servicezuul;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forezp.servicezuul.utils.AESUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceZuulApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAES() {
        JSONObject jsonObject = JSON.parseObject("{\"serviceId\":\"123456\",\"seqNo\":\"fd202bb414154c4688ec79d183520923\",\"datetime\":\"20190418182623\",\"ext\":\"\"}");
        String str1 = AESUtil.Encrypt(jsonObject.toJSONString(), "hehehehehehehehe", 1);
        System.out.println("decryptResult:"+AESUtil.Decrypt(str1,"hehehehehehehehe",1));
        System.out.println("encryptResult:"+str1);


        String str2 =AESUtil.Encrypt("{\"serviceId\":\"123456\",\"seqNo\":\"fd202bb414154c4688ec79d183520923\",\"datetime\":\"20190418182623\",\"ext\":\"\"}", "hehehehehehehehe", 1);
        System.out.println("encryptResult:"+str2);
        System.out.println("decryptResult:"+AESUtil.Decrypt(str2,"hehehehehehehehe",1));

        System.out.println(str1.equals(str2));
    }

}
