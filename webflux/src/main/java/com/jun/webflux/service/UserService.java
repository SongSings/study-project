package com.jun.webflux.service;

import com.jun.webflux.model.User;
import reactor.core.publisher.Mono;

/**
 * @author songjun
 * date 2021-04-17
 */
public interface UserService extends IService<User,Long>{

    Mono<String> hello();
}
