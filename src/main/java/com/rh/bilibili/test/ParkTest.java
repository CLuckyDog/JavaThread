package com.rh.bilibili.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.ParkTest")
public class ParkTest {

    public static void  testPark() throws InterruptedException {
        Thread t1=new Thread(()->{
            log.debug("park....");
            LockSupport.park();
            log.debug("unpark...");
//            log.debug("打断状态：{}",Thread.interrupted());
            log.debug("打断状态：{}",Thread.currentThread().isInterrupted());

            LockSupport.park();
            log.debug("unpark...");
        },"t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        testPark();
    }
}
