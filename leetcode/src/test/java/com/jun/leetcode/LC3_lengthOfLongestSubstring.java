package com.jun.leetcode;

/**
 * 3. 无重复字符的最长子串
 */
public class LC3_lengthOfLongestSubstring {
    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
     *
     * 
     *
     * 示例1:
     *
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是"wke"，所以其长度为 3。
     *     请注意，你的答案必须是 子串 的长度，"pwke"是一个子序列，不是子串。
     * 
     *
     * 提示：
     *
     * 0 <= s.length <= 5 * 104
     * s由英文字母、数字、符号和空格组成
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/longest-substring-without-repeating-characters
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    public int lengthOfLongestSubstring(String s) {
        if(null == s) return 0;
        if(s.length()<=1) return s.length();

        char[] sChars = s.toCharArray();
        int max = 0;

        for (int i = 0; i < sChars.length; i++) {
            int loopSize = i;
            String str = "";
            boolean loop = true;
            while (loopSize<sChars.length && loop) {
                if (str.contains( String.valueOf(s.charAt(loopSize)))) {
                    loop = false;
                } else {
                    str = str + s.charAt(loopSize);
                }
                if (str.length()> max) {
                    max = str.length();
                }
                loopSize++;
            }

        }
        return max;
    }

    public static void main(String[] args) {
        LC3_lengthOfLongestSubstring lc3 = new LC3_lengthOfLongestSubstring();
        int result = lc3.lengthOfLongestSubstring("pwwkewa");
        System.out.println("result = " + result);
    }
}
