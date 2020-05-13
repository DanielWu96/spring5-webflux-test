package com.daniel.zuul.server.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: daniel
 * @date: 2020/5/13 14:11
 */
@Component
@Slf4j
public class AfterRoutingFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 899;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        InputStream inputStream = currentContext.getResponseDataStream();
        byte[] bytes = new byte[1024];
        int b;
        while (true) {
            try {
                if (!((b = inputStream.read(bytes)) != -1)) break;
                String s = new String(bytes, "utf-8");
                System.out.println(s);
            } catch (IOException e) {
                log.error("io异常");
            }
        }
        return null;
    }
}
