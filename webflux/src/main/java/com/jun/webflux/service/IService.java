package com.jun.webflux.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author songjun
 * @date 2021-04-19
 * @desc
 */
public interface IService<T,U> {

    Mono<?> findById(U u);

    Flux<?> findAll();

    Mono<?> save(T t);

    Mono<?> delete(U u);

    Mono<?> modify(T t);
}
