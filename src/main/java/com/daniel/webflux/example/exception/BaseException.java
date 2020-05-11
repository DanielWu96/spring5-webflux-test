package com.daniel.webflux.example.exception;

import com.daniel.webflux.example.common.RestStatus;

/**
 * 异常基类，所有异常都要继承这个类
 *
 * @author daniel
 * @since 2020/3/31 18:29
 */
public class BaseException extends RuntimeException implements RestStatus {
    private String code;
    private String msg;

    public BaseException(RestStatus errorStatus) {
        super();
        this.code = errorStatus.getCode();
        this.msg = errorStatus.getMsg();
    }

    public BaseException(RestStatus errorStatus, Throwable cause) {
        super(cause);
        this.code = errorStatus.getCode();
        this.msg = errorStatus.getMsg();
    }

    public BaseException(RestStatus errorStatus, String replaceMsg) {
        super();
        this.code = errorStatus.getCode();
        this.msg = replaceMsg;
    }

    public BaseException(RestStatus errorStatus, String replaceMsg, Throwable cause) {
        super(cause);
        this.code = errorStatus.getCode();
        this.msg = replaceMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
