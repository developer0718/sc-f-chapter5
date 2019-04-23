package com.netzoom.servicezuul;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netzoom.servicezuul.utils.AESUtil;
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


        String str2 =AESUtil.Encrypt("{\"header\":{\"datetime\":\"20190418182923\",\"ext\":\"\",\"seqNo\":\"cef1dcf7dd24466eaf950335aa3a1928\",\"serviceId\":\"258963\"},\"body\":{\"success\":true,\"code\":10000,\"msg\":\"处理成功\",\"data\":{\"UID\":\"46784654164\",\"mobile\":\"13245678952\",\"name\":\"张三\"}},\"sign\":\"89697E2CA0A9A0A865CF3B7F005D6984\"}", "hehehehehehehehe", 1);
        System.out.println("encryptResult:"+str2);
        System.out.println("decryptResult:"+AESUtil.Decrypt(str2,"hehehehehehehehe",1));

        System.out.println(str1.equals(str2));
    }

    @Test
    public void testAES_faile() {
        JSONObject jsonObject = JSON.parseObject("{\"serviceId\":\"123456\",\"seqNo\":\"fd202bb414154c4688ec79d183520923\",\"datetime\":\"20190418182623\",\"ext\":\"\"}");
        String str1 = AESUtil.Encrypt(jsonObject.toJSONString(), "hehehehehehehehe", 1);
        System.out.println("decryptResult:"+AESUtil.Decrypt(str1,"hehehehehehehehe",1));
        System.out.println("encryptResult:"+str1);


        String str2 =AESUtil.Encrypt("{\"1header\":{\"datetime\":\"20190418182923\",\"ext\":\"\",\"seqNo\":\"cef1dcf7dd24466eaf950335aa3a1928\",\"serviceId\":\"258963\"},\"body\":{\"success\":true,\"code\":10000,\"msg\":\"处理成功\",\"data\":{\"UID\":\"46784654164\",\"mobile\":\"13245678952\",\"name\":\"张三\"}},\"sign\":\"89697E2CA0A9A0A865CF3B7F005D6984\"}", "hehehehehehehehe", 1);
        System.out.println("encryptResult:"+str2);
        System.out.println("decryptResult:"+AESUtil.Decrypt(str2,"hehehehehehehehe",1));

        System.out.println(str1.equals(str2));
    }
}
