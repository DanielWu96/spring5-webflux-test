package com.daniel.webflux.example.common;

import org.springframework.util.StringUtils;

/**
 * @author daniel
 * @Date 2019/12/5 15:25:18
 */
public enum CommonRespEnum implements RestStatus {
    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    BAD_REQUEST_PARAM("400", "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401", "请求的数字签名不匹配!"),
    SERVER_LOAD_ERROR("403","改接口已被限流"),
    NOT_FOUND("404", "api not found!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503", "服务器正忙，请稍后再试!");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述
     */
    private String msg;

    CommonRespEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CommonRespEnum getEnum(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (CommonRespEnum commonRespEnum : CommonRespEnum.values()) {
            if (commonRespEnum.code.equals(code)) {
                return commonRespEnum;
            }
        }
        return null;
    }


    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
