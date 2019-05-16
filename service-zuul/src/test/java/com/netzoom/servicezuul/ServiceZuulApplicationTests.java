package com.netzoom.servicezuul;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netzoom.servicezuul.apimanager.security.service.MyInvocationSecurityMetadataSourceService;
import com.netzoom.servicezuul.utils.AESUtil;
import com.netzoom.servicezuul.utils.EncryptUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
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
      /*  JSONObject jsonObject = JSON.parseObject("{\"serviceId\":\"123456\",\"seqNo\":\"fd202bb414154c4688ec79d183520923\",\"datetime\":\"20190418182623\",\"ext\":\"\"}");
        String str1 = AESUtil.Encrypt(jsonObject.toJSONString(), "hehehehehehehehe", 1);
        System.out.println("decryptResult:"+AESUtil.Decrypt(str1,"hehehehehehehehe",1));
        System.out.println("encryptResult:"+str1);*/


        String str2 =AESUtil.Encrypt("{\"pageIndex\":1,\"pageSize\":10}", "96621bac8f5948fa97792138f635b49c", 1);
        System.out.println("encryptResult:"+str2);
        System.out.println("decryptResult:"+AESUtil.Decrypt(str2,"96621bac8f5948fa97792138f635b49c",1));
        String tempSign = EncryptUtil.md5Encode("pageIndex=1&pageSize=10&secretkey=c70815ad156d4ddb9839bc8af1b7b6f6");
        System.out.println("tempSign:"+tempSign);
    }

    @Test
    public  void testJson(){
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("2","test");
        jsonObject.put("1","test");
        jsonObject.put("5","test");
        jsonObject.put("0","test");
        jsonObject.put("hehe","haha");

        System.out.println(jsonObject.toJSONString());
        System.out.println(jsonObject.getString("hehe"));
        System.out.println(jsonObject.getString("hehe1"));

        String s = JSONObject.parse("{\"2\":\"test\",\"1\":\"test\",\"5\":\"test\",\"0\":\"test\"}").toString();
    }

    @Test
    public void hehe(){
        String data = "{\n" +
                "\t\"header\": {\n" +
                "\t\t\"datetime\": \"20190509174510\",\n" +
                "\t\t\"seqNo\": \"8c905580-ba83-4b0e-8346-06e91f7d7bd9\",\n" +
                "\t\t\"serviceId\": 1,\n" +
                "\t\t\"sign\": \"$2a$10$rmQSg3FBp/HbyDuCi1iVLe5XFNjrXQDfjIfOicnEfl8ZPbOXruP9q\",\n" +
                "\t\t\"ext\": \"\"\n" +
                "\t},\n" +
                "\t\"body\": \"nsG6eLvLdcTwdqrzGa8Bj08Vr3JD5UE9UuamYGC/TwFkzg32EPL7j8FeFBkLkcKg\",\n" +
                "\t\"sign\": \"524C0EDD0559554220425256E23552F2\"\n" +
                "}";
        JSONObject requestData = JSONObject.parseObject(data);
        JSONObject header= requestData.getJSONObject("header");
        System.out.println("requestData"+requestData);
        System.out.println("header"+header);
        String serviceId= header.getString("serviceId");
        String sign = header.getString("sign");
    }

    @Autowired
    MyInvocationSecurityMetadataSourceService metadataSourceService;

    @Test
    public void testtttt(){
        metadataSourceService.loadResourceDefine();
    }

    @Test
    public void  test1(){
        String s = "/actuator/health";
        System.out.println(s.indexOf("/"));
        System.out.println(s.substring(0, s.indexOf("/",s.indexOf("/")+1 )));
        System.out.println( s.split("/").length);
        //s.split("/");


    }
}
