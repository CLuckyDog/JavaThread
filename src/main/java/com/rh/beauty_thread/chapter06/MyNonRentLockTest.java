package com.rh.beauty_thread.chapter06;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: MyNonRentLockTest
 * @Description:
 * @author:pzj
 * @date: 2021/2/19 15:54
 */
@Slf4j(topic = "c.MyNonRentLockTest")
public class MyNonRentLockTest {
    final static NonReentrantLock lock = new NonReentrantLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue= new LinkedBlockingQueue<>();
    final static int queueSize = 10;
    public static void main(String[] args) {
        Thread producer = new Thread(() -> {
            lock.lock();
            try {
                //1、如果队列满了，则等待
                while (queue.size()==queueSize){
                    try {
                        notEmpty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //2、添加元素到队列中
                String ele="ele"+System.currentTimeMillis();
                log.debug("producer 添加元素："+ele);
                queue.add(ele);
                //3、唤醒消费线程
                notFull.signalAll();
            }finally {
                lock.unlock();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            lock.lock();
            try {
                //1、如果队列空，则等待
                while (queue.size()==0){
                    try {
                        notFull.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //2、消费一个元素
                String ele = queue.poll();
                log.debug("consumer 取出元素: "+ele);
                //3、唤醒生产线程
                notEmpty.signalAll();
            }finally {
                lock.unlock();
            }
        }, "consumer");

        producer.start();
        consumer.start();
    }
}
