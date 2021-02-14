package com.rh.bilibili.chapter04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.rh.bilibili.utils.Sleeper.sleep;

@Slf4j(topic = "c.TestLockSupport")
public class TestLockSupport {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread t1=new Thread(()->{
            log.debug("-----1-----");
            sleep(1);
            LockSupport.park();
            LockSupport.park();
            log.debug("-----2-----");
//            LockSupport.unpark(mainThread);
        },"t1");

        t1.start();

        sleep(2);
//        LockSupport.park();
        LockSupport.unpark(t1);
        LockSupport.unpark(t1);
//        sleep(1);
//        LockSupport.unpark(t1);
    }
}
