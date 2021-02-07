package com.rh.design_thread.chapter_three;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2019/10/3.
 *
 * 读写锁示例代码
 */
public class ReadWriteLockDemo {
    private static Lock lock=new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    private static Lock readLock=readWriteLock.readLock();
    private static Lock writeLock=readWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getId()+"ReadThread in ...");
            Thread.sleep(1000);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock,int index) throws InterruptedException {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getId()+"WriteThread in ...");
            Thread.sleep(1000);
            value=index;
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        final ReadWriteLockDemo demo=new ReadWriteLockDemo();
        Runnable readRunnable=new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleRead(readLock);
//                    demo.handleRead(lock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writeRunnable=new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleWrite(writeLock,new Random().nextInt());
//                    demo.handleWrite(lock,new Random().nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        /*
        * 创建18个读线程，他们是并行的
        * */
        for (int i=0;i<18;i++){
            new Thread(readRunnable).start();
        }
//        Thread.sleep(1000);
        /*
        * 创建两个写线程，他们是串行的，会出现堵塞现象。
        * */
        for (int i=18;i<20;i++){
            new Thread(writeRunnable).start();
        }
    }
}
