package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestOrderThread2
 * @Description: 使用LockSupport类实现线程顺序工作
 * @author: pzj
 * @date: 2021/2/15 16:56
 */
@Slf4j(topic = "c.TestOrderThread2")
public class TestOrderThread2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("-----t1-----");
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("-----t2-----");
            LockSupport.unpark(t1);
        }, "t2");

        t1.start();
        t2.start();
    }
}