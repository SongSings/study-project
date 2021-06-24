package com.jun.jpa.domain;

import com.jun.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 数据持久层操作接口
 *
 */
public interface UserRepository extends JpaRepository<User, Long>
        ,PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

}
