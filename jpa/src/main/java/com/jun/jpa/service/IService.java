package com.jun.jpa.service;

/**
 * @author songjun
 * @date 2021-04-19
 * @desc
 */
public interface IService<T,U> {

    T findById(U u);
}
