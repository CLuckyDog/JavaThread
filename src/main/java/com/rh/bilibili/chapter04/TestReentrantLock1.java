package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestReentrantLock1
 * @Description: 测试重入锁可打断的方法
 * @author: pzj
 * @date: 2021/2/15 11:12
 */
@Slf4j(topic = "c.TestReentrantLock1")
public class TestReentrantLock1 {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        final Thread t1 = new Thread(() -> {
            try {
                log.debug("尝试获取锁");
                lock.lockInterruptibly();//可打断的获取锁
                log.debug("获取到锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获取锁，返回");
                return;
            } finally {
                log.debug("释放锁资源");
                lock.unlock();
            }
        }, "t1");

        lock.lock();//主线程先获取锁，那么t1线程竞争锁失败，将进入BLOCK状态
        t1.start();

        sleep(1);
        log.debug("打断 t1");
        t1.interrupt();//打断t1的等待锁状态，则会进入cache块，终止t1的BLOCK状态
    }
}