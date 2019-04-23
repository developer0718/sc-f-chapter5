package com.netzoom.servicezuul.apimanager.model;

/**
 * 数组返回承载类
 * @author TanzJ
 */
public class ResponseData {

    Object data;

    public ResponseData() {
    }

    public ResponseData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
