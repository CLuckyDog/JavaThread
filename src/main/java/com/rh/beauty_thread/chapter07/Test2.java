package com.rh.beauty_thread.chapter07;

import com.rh.beauty_thread.chapter06.Test3;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/3/18
 * \* Time: 14:46
 * \* Description:
 * \
 */
public class Test2 {

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> threadLocal= new ThreadLocal();

        tl1(threadLocal);
        threadLocal=null;
        runGc();
        Thread.sleep(1000);
        tl2(threadLocal);

    }

    public static void tl1(ThreadLocal<String> threadLocal){
        threadLocal.set("xx");
    }

    public static void tl2(ThreadLocal<String> threadLocal){
        System.out.println(threadLocal.get());
    }

    public static void runGc() {
        Runtime.getRuntime().gc();
        enqueueReferences();
        System.runFinalization();
    }

    private static void enqueueReferences() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException var2) {
            throw new AssertionError();
        }
    }
}