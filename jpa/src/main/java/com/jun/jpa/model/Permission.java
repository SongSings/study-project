package com.jun.jpa.model;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.*;

/**
 * @author songjun
 * @date 2021-06-24
 * @desc
 */

@Data
@Entity
@Table(name = "permission")
@org.hibernate.annotations.Table(appliesTo = "permission", comment = "权限表")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiParam("主键ID")
    @Column(name = "id", length = 20)
    protected Long id;

    @ApiParam("权限名称")
    @Column(name = "permission_name", columnDefinition = "VARCHAR(64) NOT NULL COMMENT '权限名称'")
    private String permissionName;
}
