package com.feng.base.exception;

/**
 * @description:
 * @author: SorryMaker
 * @create: 2023-02-24 14:08
 **/
public class CollegePlusException extends RuntimeException {
    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    public CollegePlusException() {
        super();
    }

    public CollegePlusException(String message) {
        super(message);
        this.errMessage = message;
    }
    public static void  cast(String errMessage){
        throw new CollegePlusException(errMessage);
    }
    public static void  cast(CommonError commonError){
        throw new CollegePlusException(commonError.getErrMessage());
    }
}