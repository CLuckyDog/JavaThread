package com.rh.bilibili.test;

import lombok.SneakyThrows;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/1/26
 * \* Time: 10:57
 * \* Description:
 * \
 */
public class TestJoin {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("child t1 over!");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("child t2 over!");
            }
        });

        t1.start();
        t2.start();

        System.out.println("wait all child thread over !");

        t1.join();
        System.out.println("--------------------");
        t2.join();

        

        System.out.println("all child thread over !");
    }
}