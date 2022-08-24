package com.jun.sign.controller;

import com.jun.sign.config.response.ResponseResult;
import com.jun.sign.config.sign.Signature;
import com.jun.sign.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class SignTestController {

    @Signature
    @PostMapping("test/{id}")
    public ResponseResult<String> myController(@PathVariable String id
            , @RequestParam String client
            , @RequestBody User user) {
        return ResponseResult.success(String.join(",", id, client, user.toString()));
    }

    @Signature
    @PostMapping("test2")
    public ResponseResult<String> myController(@RequestBody User user) {
        return ResponseResult.success(String.join(",",  user.toString()));
    }

}
