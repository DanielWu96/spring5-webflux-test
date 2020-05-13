package com.daniel.zuul.server.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 执行routing前的过滤器
 * @Author: daniel
 * @date: 2020/5/13 11:11
 */
@Component
@Slf4j
public class PreRoutingFilter extends ZuulFilter {
    /**
     * 过滤器类型，有pre、routing、post、error四种。
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器执行顺序，数值越小优先级越高。
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否进行过滤，返回true会执行过滤。
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 自定义的过滤器逻辑，当shouldFilter()返回true时会执行。
     */
    @Override
    public Object run(){
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String auth=request.getHeader("Authorization");
        if(!StringUtils.hasText(auth)){
            // 没有token，登录校验失败，拦截
            currentContext.setSendZuulResponse(false);
            // 返回401状态码。也可以考虑重定向到登录页。
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            log.warn("request {} miss authorization，return 401",request.getRequestURI());
        }
        currentContext.addZuulRequestHeader("apiKey","jojo");
        currentContext.addZuulRequestHeader("devKey","daniel");
        return null;
    }
}
