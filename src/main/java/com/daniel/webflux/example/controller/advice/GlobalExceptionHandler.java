package com.daniel.webflux.example.controller.advice;


import com.daniel.webflux.example.common.CommonRespEnum;
import com.daniel.webflux.example.common.RequestContext;
import com.daniel.webflux.example.domain.ApiResult;
import com.daniel.webflux.example.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

/**
 * 全局异常捕捉
 *
 * @author daniel
 * @Date 2019/12/5 15:43:17
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理一般业务异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Mono<ApiResult> BaseExceptionHandler(BaseException exception) {
        return Mono.subscriberContext()
                .map(context -> {
                    RequestContext requestContext=context.get(RequestContext.class);
                    String uri = requestContext.getUri();
                    String reqId = requestContext.getReqId();
                    log.warn("reqId:{}, uri:{}, 一般业务异常:{}", reqId, uri, exception.getMsg());
                    return ApiResult.error(exception);
                });
    }

    /**
     * 接口参数处理异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ServerWebInputException.class)
    @ResponseBody
    public Mono<ApiResult> exceptionHandler(ServerWebInputException exception) {
        return Mono.subscriberContext()
                .map(context -> {
                    RequestContext requestContext=context.get(RequestContext.class);
                    String uri = requestContext.getUri();
                    String reqId = requestContext.getReqId();
                    log.warn("reqId:{}, uri:{}, 接口参数处理异常:{}", reqId, uri, exception.getReason());
                    return ApiResult.error(CommonRespEnum.BAD_REQUEST_PARAM);
                });
    }

    /**
     * 处理其他异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Mono<ApiResult> exceptionHandler(Exception exception) {
        return Mono.subscriberContext()
                .map(context -> {
                    RequestContext requestContext=context.get(RequestContext.class);
                    String uri = requestContext.getUri();
                    String reqId = requestContext.getReqId();
                    log.error("reqId:{}, uri:{}, occur error.{}", reqId, uri, exception);
                    return ApiResult.error(CommonRespEnum.INTERNAL_SERVER_ERROR);
                });
    }
}
