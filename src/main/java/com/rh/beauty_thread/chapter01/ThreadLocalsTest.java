package com.rh.beauty_thread.chapter01;

import java.util.concurrent.ThreadLocalRandom;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/2/1
 * \* Time: 9:45
 * \* Description:
 * \
 */
public class ThreadLocalsTest {
    private static ThreadLocal<String> local1 = new ThreadLocal();
    private  static  ThreadLocal<String> local2 = new ThreadLocal();

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            local1.set("1111");
            local2.set("2222");
            System.out.println("t1-local1:"+local1.get());
            System.out.println("t1-local2:"+local2.get());
        },"t1");

        Thread t2 = new Thread(()->{
            local1.set("3333");
            local2.set("4444");
            System.out.println("t2-local1:"+local1.get());
            System.out.println("t2-local2:"+local2.get());
        },"t2");

        t1.start();
        t2.start();

        System.out.println("main:"+local1.get());
        System.out.println("main:"+local2.get());

        ThreadLocalRandom current = ThreadLocalRandom.current();
    }
}