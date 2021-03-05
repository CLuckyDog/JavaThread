package com.rh.bilibili.test;

import java.util.concurrent.atomic.AtomicInteger;

public class Test1 {
    public static void main(String[] args) {
//        new Thread(() -> {
//            while(true) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "t1").start();
//
//        new Thread(() -> {
//            while(true) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "t2").start();
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

    }

     private static String name;

    static class X{

        public String getName(){
            name="x";
            return name;
        }
    }
}

