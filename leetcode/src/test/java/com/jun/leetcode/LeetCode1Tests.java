package com.jun.leetcode;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author songjun
 * @date 2021-07-01
 * @desc 6
 */
@SpringBootTest
public class LeetCode1Tests {

    // 01.02. 判定是否互为字符重排
    public static void main(String[] args) {
        boolean b = CheckPermutation("abc", "bad");
        System.out.println("b = " + b);
    }


    public static boolean CheckPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        HashMap<Character, Integer> hashMap1 = new HashMap();
        HashMap<Character, Integer> hashMap2 = new HashMap();
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();

        for (int i = 0; i < s1.length(); i++) {
            hashMap1.put(c1[i], hashMap1.getOrDefault(c1[i], 0) + 1);
            hashMap2.put(c2[i], hashMap2.getOrDefault(c2[i], 0) + 1);
        }

        for (Character c : hashMap2.keySet()) {
            if (!Objects.equals(hashMap1.get(c),hashMap2.get(c))) {
                return false;
            }
        }
        return true;

    }

    // 01.03. URL化
    public String replaceSpaces(String S, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = S.charAt(i);
            if(ch == ' '){
                stringBuilder.append("%20");
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }

    // 01.06. 字符串压缩
    public String compressString(String S) {
        if (S.length() == 0) { // 空串处理
            return S;
        }
        char ch = S.charAt(0);
        StringBuilder stringBuilder = new StringBuilder();
        int num = 1;
        for (int i = 1; i < S.length(); i++) {
            if (ch == S.charAt(i)) {
                num++;
            } else {
                stringBuilder.append(ch).append(num);
                ch = S.charAt(i);
                num = 1;
            }
        }
        String s = stringBuilder.append(ch).append(num).toString();
        return s.length() >= S.length() ? S : s;
    }
}
