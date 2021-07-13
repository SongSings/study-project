package com.john.kafka.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author songjun
 * @date 2021-07-13
 * @desc 消息实体类
 */
@Data
public class Message {

    private Long id;

    private String msg;

    private Date sendTime;
}
