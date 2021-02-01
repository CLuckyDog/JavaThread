package com.rh.bilibili.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.CounterTest")
public class CounterTest {
    private static int counter = 0;
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 5000; i++) {
                    counter++;
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 5000; i++) {
                    counter--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(counter);
    }
}
