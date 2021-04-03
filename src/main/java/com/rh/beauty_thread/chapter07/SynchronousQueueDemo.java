package com.rh.beauty_thread.chapter07;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: SynchronousQueueDemo
 * @Description:
 * @author: pzj
 * @date: 2021/4/3 11:57
 */
@Slf4j(topic = "c.SynchronousQueueDemo")
public class SynchronousQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>(true);

        Thread putThread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("put1 thread start");
//                System.out.println("put thread start");
                try {
                    queue.put(1);
                } catch (InterruptedException e) {
                }
//                System.out.println("put thread end");
                log.debug("put1 thread end");
            }
        });

        Thread putThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("put2 thread start");
//                System.out.println("put thread start");
                try {
                    queue.put(2);
                } catch (InterruptedException e) {
                }
//                System.out.println("put thread end");
                log.debug("put2 thread end");
            }
        });

        Thread takeThread = new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("take thread start");
                log.debug("take thread start");
                try {
                    log.debug("take from putThread: " + queue.take());
//                    System.out.println("take from putThread: " + queue.take());
                } catch (InterruptedException e) {
                }
                log.debug("take thread end");
//                System.out.println("take thread end");
            }
        });

        putThread.start();
        Thread.sleep(1000);
        putThread2.start();
        Thread.sleep(1000);
        takeThread.start();
    }
}