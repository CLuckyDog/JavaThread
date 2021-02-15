package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestReentrantLock2
 * @Description: 尝试获取锁 tryLock 的API 学习
 * 光标在方法名上，CTRL+Q查看方法的API说明
 * 光标在方法的括号内，CTRL+Q查看对应的重载方法
 * @author: pzj
 * @date: 2021/2/15 11:31
 */
@Slf4j(topic = "c.TestReentrantLock2")
public class TestReentrantLock2 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获到锁");
             boolean lockResult=false;
            try {
                lockResult = lock.tryLock();//不带时间参数的，立刻返回获取锁的结果
                //带时间参数的，在时间范围内，尝试获取锁，获取到就立刻返回，获取不到，超时后返回FALSE
                lockResult = lock.tryLock(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                log.debug("未能获取到锁！--");
                return;
            }
            if (lockResult) {
                try {
                    log.debug("获取到锁");
                } finally {
                    lock.unlock();
                }
            } else {
                log.debug("未能获取到锁！");
            }

        }, "t1");

        lock.lock();//main线程先获取到锁
        log.debug("main 获取到锁");
        t1.start();
        sleep(1);
        log.debug("main 释放了锁");
        lock.unlock();
    }
}