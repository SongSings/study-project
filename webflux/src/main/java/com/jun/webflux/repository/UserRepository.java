package com.jun.webflux.repository;

import com.jun.webflux.model.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author songjun
 * @date 2021-04-14
 * @desc
 */
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

//    @Modifying
//    @Query("insert into user (id,user_name,sex,age,birthday,email,content) values (:id,:userName,:sex,:age,:birthday,:email,:content)")
//    Mono<Integer> save(User user);

//    @Modifying
//    @Query("delete from user where id = :id")
//    Mono<Integer> delete(Long id);

}
