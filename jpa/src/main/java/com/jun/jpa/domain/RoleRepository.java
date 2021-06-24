package com.jun.jpa.domain;

import com.jun.jpa.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 数据持久层操作接口
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

}
