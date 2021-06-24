package com.jun.jpa.service;

import com.jun.jpa.dao.UserQueryVo;
import com.jun.jpa.model.User;
import org.springframework.data.domain.Page;

/**
 * @author songjun
 * date 2021-04-17
 */
public interface UserService extends IService<User,Long>{

    Page<User> tables(UserQueryVo userQueryVo);
}
