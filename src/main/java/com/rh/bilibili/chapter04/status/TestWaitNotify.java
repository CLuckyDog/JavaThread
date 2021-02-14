package com.rh.bilibili.chapter04.status;

import com.rh.bilibili.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestWaitNotify")
public class TestWaitNotify {

    private static Object lock = new Object();

    public static void main(String[] args) {

        new Thread(()->{
            synchronized (lock){
                try {
                    log.debug("执行。。。");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("执行其他代码。");
            }
        },"t1").start();

        new Thread(()->{
            synchronized (lock){
                try {
                    log.debug("执行。。。");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("执行其他代码。");
            }
        },"t2").start();

        Sleeper.sleep(1);
        log.debug("唤醒 lock 上其他线程。。。");
        synchronized (lock){
            lock.notifyAll();
        }


    }
}
