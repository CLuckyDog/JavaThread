package com.rh.bilibili.test;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/1/26
 * \* Time: 11:15
 * \* Description:
 * \
 */
public class TestInterruptedException {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 start!");
                while (true){

                }
            }
        });

        Thread mainThread = Thread.currentThread();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                mainThread.interrupt();
                t1.interrupt();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}