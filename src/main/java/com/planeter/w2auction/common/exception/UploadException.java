package com.planeter.w2auction.common.exception;

public class UploadException extends RuntimeException{
    private String msg;

    public UploadException(String msg){
        super(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
