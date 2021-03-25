package com.example.es.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author songjun
 * @date 2021-01-12
 * @desc
 */
@Accessors(fluent = true)
@Data
public class QueryDTO {

    private Integer current;

    private Integer size;

    private Long fromTime;

    private Long endTime;

    private String username;

}
