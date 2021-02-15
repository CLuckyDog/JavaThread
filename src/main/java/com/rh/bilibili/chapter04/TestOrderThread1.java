package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestOrderThread1
 * @Description: 用ReentrantLock实现线程顺序执行
 * @author: pzj
 * @date: 2021/2/15 16:49
 */
@Slf4j(topic = "c.TestOrderThread1")
public class TestOrderThread1 {
    static boolean t2runned = false;

    static ReentrantLock reLock=new ReentrantLock();
    static Condition condition = reLock.newCondition();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            reLock.lock();
            try {
                while (!t2runned){//t2没有执行过，则进入等待
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("---------t1----------");
            }finally {
                reLock.unlock();
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            reLock.lock();
            try {
                t2runned = true;
                log.debug("---------t2----------");
                condition.signal();
            }finally {
                reLock.unlock();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}