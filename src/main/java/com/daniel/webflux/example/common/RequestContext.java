package com.daniel.webflux.example.common;

import lombok.Data;

/**
 * @Author: daniel
 * @date: 2020/4/3 14:14
 */
@Data
public class RequestContext {

    private String reqId;
    private String uri;
}
