package com.forezp.servicezuul.filter.common;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class ServletResponseWrapper extends HttpServletResponseWrapper {

    private  volatile MonitorOutputStream mos;

    public ServletResponseWrapper(HttpServletResponse response) {
        super(response);
        // TODO Auto-generated constructor stub
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        // TODO Auto-generated method stub
        if(mos==null){
            synchronized (this) {
                if(mos==null){
                    mos = new  MonitorOutputStream(super.getOutputStream());
                }
            }
        }
        return mos;
    }

    public String getResponseBody(){

        return new String(mos.getWroteInfo());


    }
}

