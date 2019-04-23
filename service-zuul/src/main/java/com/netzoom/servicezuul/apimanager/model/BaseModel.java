package com.netzoom.servicezuul.apimanager.model;


/**
* 组件间消息传输载体
* @author TanzJ
* @CreateTime 2018/12/26 21:54
*/
public class BaseModel {

    private String result;
    private Object message;


    public BaseModel() {
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public BaseModel(String result, Object message) {
        this.result = result;
        this.message = message;
    }
}
