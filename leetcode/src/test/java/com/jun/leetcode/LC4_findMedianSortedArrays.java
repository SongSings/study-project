package com.jun.leetcode;

import java.util.Arrays;

public class LC4_findMedianSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int [] newArray = new int[nums1.length+nums2.length];

        for (int i = 0; i < nums1.length; i++) {
            newArray[i] = nums1[i];
        }
        for (int i = 0; i < nums2.length; i++) {
            newArray[i+nums1.length] = nums2[i];
        }
        if(newArray.length == 0) return 0;

        if(newArray.length == 1){
            return newArray[0];
        }

        int[] ints = Arrays.stream(newArray).sorted().toArray();
        if(ints.length %2 == 0){
            int avg = ints.length / 2;

            if(avg>0){
                return (double)(ints[avg-1]+ints[avg])/2;
            } else {
                return 0;
            }
        } else{
            int avg = ints.length / 2;

            if(avg>0){
                return (ints[avg]);
            } else {
                return 0;
            }
        }
    }


    public static void main(String[] args) {
        LC4_findMedianSortedArrays lc4_findMedianSortedArrays = new LC4_findMedianSortedArrays();
        double medianSortedArrays = lc4_findMedianSortedArrays.findMedianSortedArrays(new int[]{1, 2}, new int[]{3,4});
        System.out.println("medianSortedArrays = " + medianSortedArrays);
    }
}
