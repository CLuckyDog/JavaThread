package com.rh.bilibili.chapter04;

import com.rh.bilibili.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestDeadLock")
public class TestDeadLock {
    private static Object ALock = new Object();
    private static Object BLock = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (ALock){
                log.debug("lock A...");
                Sleeper.sleep(1);
                synchronized (BLock){
                    log.debug("lock B...");
                    log.debug("其他操作...");
                }
            }
        },"t1").start();

        new Thread(()->{
            synchronized (BLock){
                log.debug("lock B...");
                Sleeper.sleep(1);
                synchronized (ALock){
                    log.debug("lock A...");
                    log.debug("其他操作...");
                }
            }
        },"t2").start();
    }
}
