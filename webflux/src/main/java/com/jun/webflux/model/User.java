package com.jun.webflux.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author songjun
 * @date 2021-04-14
 * @desc
 */
@Data
@Table("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Integer age;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String nickName;

    private String position;

    @ApiModelProperty(value = "用户名")
    private String userName;

    private Long roleId;
}
