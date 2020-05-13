package com.daniel.webflux.example.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * reactive模式的http请求，以我的理解和feign还是有区别，feign属于声明式的web服务客户端。类似于RestTemplate,
 * 多配置了一个reactive的负载均衡器
 * @Author: daniel
 * @date: 2020/5/13 16:18
 */
@Component
public class WebfluxTestClient {

    WebClient webClient;

    /**
     * reactive模式下的负载均衡器，默认情况下使用LoadBalancerExchangeFilterFunction，
     * 如果想用ReactorLoadBalancerExchangeFilterFunction，需要设置
     * spring.cloud.loadbalancer.ribbon.enabled=false,
     * {@see application.yml}中已配置；
     * 或者删除spring-cloud-starter-netflix-ribbon引用。
     * 但是设置后原来的{@link FeignTestClient}将无法使用
     */
    @Autowired
    public WebfluxTestClient(ReactorLoadBalancerExchangeFilterFunction lbFunction){
        this.webClient= WebClient.builder()
                .baseUrl("http://test-webflux-client")
                .filter(lbFunction)//启用负载均衡
                .build();
    }

    public Mono<String> users(String value){
        return webClient.get()//get方法请求
                .uri("testclient/users?"+"phone="+value)
//                        ,new HashMap(){{
//                    put("phone",value);
//                }})
                .retrieve()
                .bodyToMono(String.class);
    }
}
