package com.daniel.webflux.example.exception;



import com.daniel.webflux.example.constants.RedisConstants;

import java.text.MessageFormat;

import static com.daniel.webflux.example.common.CommonRespEnum.INTERNAL_SERVER_ERROR;


public class RedisTimeoutException extends BaseException {

    public RedisTimeoutException(String reqId, String functionName) {
        super(INTERNAL_SERVER_ERROR, MessageFormat.format("reqId:{0}, {1} query redis time over {2} ms",
                reqId, functionName, RedisConstants.REDIS_QUERY_TIMEOUT_MILLS));
    }
}
