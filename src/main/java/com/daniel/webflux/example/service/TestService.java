package com.daniel.webflux.example.service;

import com.daniel.webflux.example.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

/**
 * @Author: daniel
 * @date: 2020/3/31 13:48
 */
@Service
public class TestService {

    public Mono<User> getUser(ServerRequest request) {
        Mono<User> mono = request.bodyToMono(User.class)
                .onErrorResume(e -> Mono.just(new User("error",1)))
                .flatMap(u -> {
                    if (u == null) {
                        return Mono.just(new User("1", 1));
                    }
                    return Mono.just(new User("1", 1));
                });
        return mono;
    }

    public Mono<User> getNull(){
        return Mono.just(null);
    }
}
