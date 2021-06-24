package com.jun.jpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author songjun
 * @date 2021-06-23
 * @desc
 */
@Data
@Entity
@Table(name = "role")
@org.hibernate.annotations.Table(appliesTo = "role", comment = "角色表")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiParam("主键ID")
    @Column(name = "id", length = 20)
    private Long id;

    @ApiParam("角色名")
    @Column(name = "role_name", columnDefinition = "VARCHAR(64) NOT NULL COMMENT '角色名'")
    private String roleName;

    @JsonBackReference
    @OneToMany(targetEntity = User.class, mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<User> users;

    /**
     * 一对一的关系 在其中一个表维护就好了
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "permission_id", updatable = false)
    private Permission permission;
}
