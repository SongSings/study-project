package com.jun.webflux.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

/**
 * @author songjun
 * @date 2021-04-14
 * @desc
 */
@Data
@Table("user")
public class User {

    @Id
    private Long id;

    private String userName;

    private Byte sex;

    private Integer age;

    private LocalDate birthday;

    private String email;

    private String content;
}
