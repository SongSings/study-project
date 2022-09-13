package com.example.elasticsearch;

/**
 * @author songjun
 * @date 2021-03-31
 * @desc 运算符 https://cloud.tencent.com/developer/article/1336599
 */
public class OperatorTests {

    public static void main(String[] args) {

        test8();
    }
    // 位运算符（^,|,&）为单个符号

    /*
     * &（按位与）,运算符两边的条件会先换算成二进制,然后再对比计算
     * &按位与的运算规则是将两边的数转换为二进制位，然后运算最终值，运算规则即(两个为真才为真)1&1=1 , 1&0=0 , 0&1=0 , 0&0=0
     * 3的二进制位是0000 0011，5的二进制位是0000 0101 ， 那么就是011 & 101，由按位与运算规则得知，001 & 101等于0000 0001，最终值为1
     * 7的二进制位是0000 0111，那就是111 & 101等于101，也就是0000 0101，故值为5
     * (二进制对应的位数对比，都为1取1 否则为0,然后将得到的二进制再转成十进制)
     */
    public static void test1(){
        int i = 3&5;
        int j = 5&7;
        System.out.println("i = " + i);
        System.out.println("j = " + j);
    }

    /*
     * && (逻辑与)
     * &&逻辑与也称为短路逻辑与，先运算&&左边的表达式，一旦为假，后续不管多少表达式，均不再计算，一个为真，再计算右边的表达式，两个为真才为真。
     */

    /*
     * 按位或
     * |按位或和&按位与计算方式都是转换二进制再计算，不同的是运算规则(一个为真即为真)1|0 = 1 , 1|1 = 1 , 0|0 = 0 , 0|1 = 1
     * 6的二进制位0000 0110 , 2的二进制位0000 0010 , 110|010为110，最终值0000 0110，故6|2等于6
     * (转成二进制后,比较对位上的值,有一个相等，则取每个位上最大的值拼成的二进制取值)
     */
    public static void test3(){
        int i = 1|6|2; // i=7       111
        ten2Two(1); //         01
        ten2Two(6); //        110
        ten2Two(2); //         10
        System.out.println("i = " + i);
    }

    /*
     * ^（异或运算符）
     * ^异或运算符顾名思义，异就是不同，其运算规则为1^0 = 1 , 1^1 = 0 , 0^1 = 1 , 0^0 = 0
     * 5的二进制位是0000 0101 ， 9的二进制位是0000 1001，也就是0101 ^ 1001,结果为1100 , 00001100的十进制位是12
     * (即对位相同则取0 不同则取1，结果集转成十进制)
     */
    public static void test4(){
        // 十进制      二进制
        //  2        0010
        //  9        1001
        //  11       1011
        System.out.println("2^9 = " + (2^9));

    }

    /**
     * <<（左移运算符）
     * 5<<2的意思为5的二进制位往左挪两位，右边补0，5的二进制位是0000 0101 ，
     * 就是把有效值101往左挪两位就是0001 0100 ，正数左边第一位补0，负数补1，等于乘于2的n次方，十进制位结果是20
     */
    public static void test5(){
        /*
        十进制   二进制
         * 1      1
         * 4     100
         */
        System.out.println("(1<<2) = " + (1 << 2));

        System.out.println("(-1<<2) = " + (-1 << 2));
    }

    /**
     * >>（右移运算符）
     * 凡位运算符都是把值先转换成二进制再进行后续的处理，5的二进制位是0000 0101，
     * 右移两位就是把101左移后为0000 0001，正数左边第一位补0，负数补1，等于除于2的n次方，结果为1
     * (正数最小为0)
     */
    public static void test6(){
        /*
        十进制   二进制
         * 1      1
         */
        System.out.println("(1>>2) = " + (1 >> 100));

        System.out.println("(-1>>2) = " + (-1 >> 2));
    }

    /**
     * ~（取反运算符）
     *
     */
    public static void test7(){
        System.out.println("(~5) = " + (~5));
    }

    /**
     * >>> : 无符号右移
     */
    public static void test8(){
        /*
         * 0001
         * 0000 (01舍去)
         */
        System.out.println("1>>>3 = " + (1>>>3));
    }
    /**
     * 十进制转二进制
     * @param value
     */
    public static void ten2Two(int value){
        int num1 = value;
        int num2 = value;
        String result = "";
        String lastResult = "";
        while (num2 != 0) {
            num1 = num2 % 2;
            num2 = num2 / 2;
            result += num1;
        }

        //进行一下反转
        for(int i = result.length() - 1; i >= 0 ; i --){
            lastResult += result.charAt(i);
        }

        System.out.println("最终二进制：" + lastResult);
    }

    public static void binaryToDecimal(int n) {
        String res = Integer.toBinaryString(n);
        System.out.println(res);

    }
}
