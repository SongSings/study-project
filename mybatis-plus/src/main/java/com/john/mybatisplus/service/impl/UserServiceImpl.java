package com.john.mybatisplus.service.impl;

import com.john.mybatisplus.entity.User;
import com.john.mybatisplus.mapper.UserMapper;
import com.john.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author john
 * @since 2021-07-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
