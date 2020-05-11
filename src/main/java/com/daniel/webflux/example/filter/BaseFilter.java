package com.daniel.webflux.example.filter;

import brave.propagation.TraceContext;
import com.daniel.webflux.example.common.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * 自定义过滤器中最先执行，主要获取reqId
 * @Author: daniel
 * @date: 2020/5/7 10:53
 */
@Slf4j
@Component
@Order(value=0)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class BaseFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return chain.filter(exchange)
                .subscriberContext(context -> {
                    String reqId=context.get(TraceContext.class).traceIdString();
                    RequestContext requestContext = new RequestContext();
                    requestContext.setReqId(reqId);
                    requestContext.setUri(exchange.getRequest().getURI().getPath());
                    Context newContext = context.put(RequestContext.class, requestContext);
                    return newContext;
                });
    }
}
