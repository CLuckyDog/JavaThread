package com.rh.bilibili.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {
    public static void main(String[] args) {

        AtomicInteger integer = new AtomicInteger(0);
        int c =-1;
        c=integer.getAndIncrement();
        System.out.println(c);
        System.out.println(integer.get());
        System.out.println("----------------1---------------");
        System.out.println(++c == 0);
//        System.out.println(c++ == 0);
        System.out.println(c);
        System.out.println("----------------2---------------");
        int oldCap = 11;
        System.out.println("(oldCap >> 1)  :"+(oldCap >> 1));
        System.out.println("(oldCap < 64)  :"+(oldCap < 64));
        System.out.println("after (oldCap >> 1)  :"+oldCap);
        System.out.println("after (oldCap < 64)  :"+oldCap);
        int newCap = oldCap + ((oldCap < 64) ?(oldCap + 2) : (oldCap >> 1));
        System.out.println("newCap:"+newCap);
        System.out.println("----------------3---------------");
        int k = 3;
        System.out.println(k >>> 1);
        System.out.println(k >> 1);
       int COUNT_BITS = Integer.SIZE - 3;
        /**
         * 线程最大个数（假设是32位系统，则低位29位）：    0001 1111 1111 1111 1111 1111 1111 1111
         * 64位系统就是：0001 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111
         * 1110 0000 0000 0000 0000 0000 0000 0000
         * 0001 1111 1111 1111 1111 1111 1111 1111
         */
        int CAPACITY   = (1 << COUNT_BITS) - 1;
        System.out.println(CAPACITY);
        System.out.println(~CAPACITY);
        System.out.println(Integer.toBinaryString(-15));
        System.out.println(~-15);
        System.out.println(Integer.toBinaryString(~-15));
        System.out.println("----------------4---------------");
        String ip = "a";

        String patternString = "(([0,1]?\\d?\\d|2[0-4]\\d|25[0-5])\\.){3}([0,1]?\\d?\\d|2[0-4]\\d|25[0-5])";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(ip);
        if (matcher.matches()){
            System.out.println(ip);
        }else{
            System.out.println("IP 格式错误！");
        }


    }

     private static String name;

    static class X{

        public String getName(){
            name="x";
            return name;
        }
    }
}

