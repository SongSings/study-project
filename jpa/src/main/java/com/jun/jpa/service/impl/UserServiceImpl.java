package com.jun.jpa.service.impl;

import com.jun.jpa.dao.UserQueryVo;
import com.jun.jpa.domain.UserRepository;
import com.jun.jpa.model.User;
import com.jun.jpa.service.UserService;
import com.jun.jpa.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author songjun
 * @date 2021-04-17
 * @desc
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(new User());
    }

    @Override
    public Page<User> queryPage(UserQueryVo userQueryVo) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Specification<User> specification = new UserSpecification(userQueryVo);
        return userRepository.findAll(specification, pageRequest);
    }

}
