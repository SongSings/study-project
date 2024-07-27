package com.jun.observer;

import com.bestvike.linq.Linq;

/**
 * @author songjun
 * @description
 * @since 2024/7/23
 */
public class LinqTests {

    public static void main(String[] args) {
        String aggregate = Linq.of("a", "b", "c").aggregate((a, b) -> a + b);
        System.out.println("aggregate = " + aggregate);


    }
}
