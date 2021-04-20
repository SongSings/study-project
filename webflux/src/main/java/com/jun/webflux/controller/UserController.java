package com.jun.webflux.controller;

import com.jun.webflux.model.User;
import com.jun.webflux.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author songjun
 * @date 2020-11-06
 * @desc
 */
@RestController
@RequestMapping(value = "/user")
@Api(tags = {"用户"})
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping(value = "/{id}")
    public Mono<?> findUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping()
    public Flux<?> findAllUser() {
        return userService.findAll();
    }

    @PostMapping()
    public Mono<?> save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping()
    public Mono<?> modifyUser(@RequestBody User user) {
        return userService.modify(user);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<?> deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @GetMapping(value = "/hello")
    public Mono<String> hello() {
        return userService.hello();
    }
}
