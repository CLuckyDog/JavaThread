package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestReentrantLock
 * @Description: ReentrantLock 可重入锁的测试类
 * @author:pzj
 * @date: 2021/2/15 10:37
 */
@Slf4j(topic = "c.TestReentrantLock")
public class TestReentrantLock {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        lock.lock();
        try {
            log.debug("enter main....");
            System.out.println();
            m1();
        }finally {
            lock.unlock();
        }
    }

    public static void m1(){

        lock.lock();
        try {
            log.debug("enter m1....");
            m2();
        }finally {
            lock.unlock();
        }
    }

    public static void m2(){
        lock.lock();
        try {
            log.debug("enter m2....");
        }finally {
            lock.unlock();
        }
    }
}