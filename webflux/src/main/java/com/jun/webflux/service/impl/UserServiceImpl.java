package com.jun.webflux.service.impl;

import com.jun.webflux.model.User;
import com.jun.webflux.repository.UserRepository;
import com.jun.webflux.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author songjun
 * @date 2021-04-17
 * @desc
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public Mono<?> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<?> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<?> save(User user) {
        return userRepository.save(user);
    }
    @Override
    public Mono<?> delete(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<?> modify(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<String> hello() {
        return Mono.just("hello this is a spring boot webflux project!");
    }
}
