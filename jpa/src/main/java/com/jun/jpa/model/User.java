package com.jun.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author songjun
 * @date 2021-06-24
 * @desc 这里有3个表，用户(User)，角色（Role），权限（Permission）。
 * 一个用户有一个角色，1个角色可以分配给多个用户，用户对角色就是多对一。假设一个角色绑定1个权限。角色对权限就是一对一。
 */
@Data
@Entity
@Table(name = "user")
@org.hibernate.annotations.Table(appliesTo = "user", comment = "用户表")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiParam("主键ID")
    @Column(name = "id", length = 20)
    private Long id;

    @ApiParam("用户名")
    @Column(name = "user_name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '用户名'")
    private String userName;

    @ApiParam("昵称")
    @Column(name = "nick_name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '昵称'")
    private String nickName;

    @ApiParam("岗位")
    @Column(name = "position")
    private String position;

    @ApiParam("年龄")
    @Column(name = "age")
    private Integer age;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    /**
     * @ManyToOne 用户:角色 多个用户对应一个角色，当我们创建表结构时，应在多的一方去维护表关系，也就是说，应将@ManyToOne注解加在用户表中，并且设置为懒加载。
     * @JsonBackReference 生成json时该属性排除
     */
    @ManyToOne()
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private Role role;
}
