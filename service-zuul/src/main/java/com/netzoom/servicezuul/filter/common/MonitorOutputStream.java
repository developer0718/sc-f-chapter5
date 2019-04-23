package com.netzoom.servicezuul.filter.common;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MonitorOutputStream extends ServletOutputStream {

    private ServletOutputStream output;
    private ByteArrayOutputStream copy=new ByteArrayOutputStream();



    public MonitorOutputStream(ServletOutputStream output) {
        super();
        this.output = output;
    }

    @Override
    public boolean isReady() {
        // TODO Auto-generated method stub
        return output.isReady();
    }

    @Override
    public void setWriteListener(WriteListener arg0) {
        // TODO Auto-generated method stub
        output.setWriteListener(arg0);
    }

    @Override
    public void write(int b) throws IOException {
        // TODO Auto-generated method stub
        output.write(b);
        copy.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        // TODO Auto-generated method stub
        output.write(b);
        copy.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        // TODO Auto-generated method stub
        output.write(b,off,len);
        copy.write(b,off,len);
    }

    public byte[] getWroteInfo() {
        return copy.toByteArray();
    }

    @Override
    public void flush() throws IOException {
        // TODO Auto-generated method stub
        output.flush();
        copy.close();
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        output.close();
        copy.close();
    }


}