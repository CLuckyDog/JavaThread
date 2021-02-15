package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestOrderThread
 * @Description: 用sync+wait+notify实现顺序执行线程任务
 * @author: pzj
 * @date: 2021/2/15 16:42
 */
@Slf4j(topic = "c.TestOrderThread")
public class TestOrderThread {
    static Object lock = new Object();
    static boolean t2runned = false;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock){
                while (!t2runned){//t2没有执行过，则进入等待
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("---------t1----------");
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            synchronized (lock){
                t2runned = true;
                log.debug("---------t2----------");
                lock.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}