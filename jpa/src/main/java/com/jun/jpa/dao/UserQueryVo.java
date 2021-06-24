package com.jun.jpa.dao;

import lombok.Data;

/**
 * @author songjun
 * @date 2021-06-24
 * @desc
 */
@Data
public class UserQueryVo {

    private Long id;

    private String userName;

    private String roleName;

    private String permissionName;

    private String ids;

    private String nickName;

    private Integer age;
}
