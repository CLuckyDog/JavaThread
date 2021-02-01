package com.rh.bilibili.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j(topic = "c.ThreadStates")
public class ThreadStates {
    public static void main(String[] args) throws IOException {
        Thread t1=new Thread(()->{  //NEW  对应 创建
            log.debug("NEW States...");
        },"t1");

        Thread t2=new Thread(()->{
            while(true) { // RUNNABLE   对应  运行和可运行

            }
        },"t2");
        t2.start();

        Thread t3=new Thread(()->{  //TERMINATED  对应 终止
            log.debug("running...");
        },"t3");
        t3.start();

        Thread t4=new Thread(()->{
            synchronized (ThreadStates.class) {
                try {
                    Thread.sleep(1000000); // TIMED_WAITING 对应 阻塞
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t4");
        t4.start();

        Thread t5=new Thread(()->{
            try {
                t2.join(); // WAITING 对应 阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t5");
        t5.start();

        Thread t6=new Thread(()->{
            synchronized (ThreadStates.class) { // BLOCKED 对应 阻塞
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t6");
        t6.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state {}", t1.getState());
        log.debug("t2 state {}", t2.getState());
        log.debug("t3 state {}", t3.getState());
        log.debug("t4 state {}", t4.getState());
        log.debug("t5 state {}", t5.getState());
        log.debug("t6 state {}", t6.getState());
//        System.in.read();
    }
}
