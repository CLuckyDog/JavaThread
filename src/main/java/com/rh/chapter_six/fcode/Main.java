package com.rh.chapter_six.fcode;

import com.rh.chapter_five.nio.EchoClient;

import java.util.Arrays;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/15 10:25
 * @description:
 * @modified By:
 */
public class Main {
    static int[] arr={1,2,3,4,5,6,7,8,9};
    public static void main(String[] args) {
//        Arrays.stream(arr).map(x->(x%2==0?x:x+1)).forEach(System.out::println);
        Arrays.stream(arr).map(x->x=x+1).forEach(System.out::println);
        Arrays.stream(arr).forEach(System.out::println);
    }
}
