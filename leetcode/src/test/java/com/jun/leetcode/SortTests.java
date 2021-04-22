package com.jun.leetcode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author songjun
 * @date 2021-04-22
 * @desc
 */
@SpringBootTest
class SortTests {

    /**
     * 冒泡排序
     */
    @Test
    void bubbleSort() {
        int[] array = {6, 4, 1, 8, 2, 9, 3, 7, 5, 6, 4};
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int current = array[j];
                    int min = array[j + 1];
                    array[j] = min;
                    array[i] = current;
                }
            }
        }
        System.out.println("array = " + array.toString());
    }
}
