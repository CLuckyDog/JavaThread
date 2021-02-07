package com.rh.design_thread.chapter_three;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2019/10/2.
 * 公平锁示例代码
 */
public class FairLock implements Runnable{
    /*
    * 指定重入锁是公平锁
    * */
    public static ReentrantLock fairLock=new ReentrantLock(true);

    @Override
    public void run() {
        while (true){
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+"获取锁！");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock rl=new FairLock();
        Thread t1=new Thread(rl,"Thread_t1");
        Thread t2=new Thread(rl,"Thread_t2");
        t1.start();t2.start();
    }
}
