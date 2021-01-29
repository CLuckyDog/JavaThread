package com.rh.bilibili.test;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/1/26
 * \* Time: 11:15
 * \* Description:
 * \
 */
public class TestInterruptedException2 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 sleep!");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 aweak!");
            }
        });

        t1.start();
        Thread.sleep(2000);
        t1.interrupt();

    }
}