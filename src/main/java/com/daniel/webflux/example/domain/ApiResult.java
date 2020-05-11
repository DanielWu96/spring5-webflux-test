package com.daniel.webflux.example.domain;

import com.daniel.webflux.example.common.CommonRespEnum;
import com.daniel.webflux.example.common.RestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 统一响应api
 *
 * @author daniel
 * @Date 2019/12/5 15:33:50
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -3974435161868977827L;
    /**
     * 响应代码
     */
    private String code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应结果
     */
    private T data;

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public ApiResult(T data) {
        this.setCode(CommonRespEnum.SUCCESS.getCode());
        this.setMsg(CommonRespEnum.SUCCESS.getMsg());
        this.setData(data);
    }

    /**
     * 失败
     */
    public static ApiResult error(RestStatus errorStatus) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(errorStatus.getCode());
        apiResult.setMsg(errorStatus.getMsg());
        apiResult.setData(null);
        return apiResult;
    }

    /**
     * 失败
     */
    public static ApiResult error(RestStatus errorStatus, String replaceMsg) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(errorStatus.getCode());
        apiResult.setMsg(replaceMsg);
        apiResult.setData(null);
        return apiResult;
    }

    public static boolean isSuccess(ApiResult result) {
        if (result == null || !CommonRespEnum.SUCCESS.getCode().equals(result.getCode())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
