package com.jun.jpa.controller;

import com.jun.jpa.dao.UserQueryVo;
import com.jun.jpa.model.User;
import com.jun.jpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songjun
 * @date 2021-06-23
 * @desc
 */
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("query")
    public Page<?> queryUser(UserQueryVo userQueryVo){
        return userService.queryPage(userQueryVo);
    }
}
