package com.jun.leetcode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class LeetcodeApplicationTests {

    @Test
    void contextLoads() {
    }


    /**
     * 7.整数反转
     * @param x
     * @return
     */
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int tmp = x % 10;
            //判断是否 大于 最大32位整数
            if (result > 214748364 || (result == 214748364 && tmp > 7)) {
                return 0;
            }
            //判断是否 小于 最小32位整数
            if (result < -214748364 || (result == -214748364 && tmp < -8)) {
                return 0;
            }
            result =result*10 +tmp;
            x/=10;
        }
        return result;
    }

    /**
     * 9.回文数
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        String strX = String.valueOf(x);
        int length = strX.length();
        char[] chars = strX.toCharArray();
        for (int i = 0; i < length/2; i++) {
            if(chars[i] != chars[length-(i+1)]){
                return false;
            }
        }
        return true;
    }

    /**
     * 136
     * @param nums
     * @return
     */
    public static int singleNumber(int[] nums) {
        HashMap<Integer, Integer> hashMap = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(nums[i])) {
                hashMap.put(nums[i], hashMap.get(nums[i]) + 1);
            } else {
                hashMap.put(nums[i], 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue().equals(1)){
                return entry.getKey();
            }
        }
        return  -1;
    }

    public static int singleNumber2(int[] nums) {
        int num = nums[0];
        for (int i = 1; i < nums.length; i++) {
            num = (num ^ nums[i]);
        }
        return num;
    }

    public static void main(String[] args) {
        int i = singleNumber2(new int[]{4, 1, 2, 1, 2});
        System.out.println("i = " + i);;
    }

}
