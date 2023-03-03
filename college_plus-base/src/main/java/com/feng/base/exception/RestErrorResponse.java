package com.feng.base.exception;

import java.io.Serializable;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-02-24 14:39
 **/
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}