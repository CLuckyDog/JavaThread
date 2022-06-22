package com.rh.bilibili.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {

            }
        };

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
            }
        };

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

    }
}
