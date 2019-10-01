package com.rh.chapter_three;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2019/10/1.
 * 重入锁示例代码
 */
public class ReenterLock implements Runnable {
    public static ReentrantLock lock=new ReentrantLock();
    public static int i=0;
    @Override
    public void run() {
        for (int j=0;j<1000000;j++){
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock tl=new ReenterLock();
        Thread t1=new Thread(tl);
        Thread t2=new Thread(tl);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println("i="+i);
    }
}
