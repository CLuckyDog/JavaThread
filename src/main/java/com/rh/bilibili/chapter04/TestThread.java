package com.rh.bilibili.chapter04;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/4/20
 * \* Time: 17:32
 * \* Description:
 * \
 */
public class TestThread {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {

            }
        });
        thread.start();
        System.out.println(thread.getId());
    }
}