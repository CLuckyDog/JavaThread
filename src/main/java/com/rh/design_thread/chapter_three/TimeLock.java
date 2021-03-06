package com.rh.design_thread.chapter_three;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2019/10/1.
 * 锁申请等待限时
 *
 * 现实比喻：
 *      你和朋友约好去打球，如果朋友迟迟不来，又无法联系到他。那么，在等待1~2个小时后，
 *      我想大部分人都会自发的扫兴离去。
 */
public class TimeLock implements Runnable {
    public static ReentrantLock lock=new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(6000);
            }else {
                System.out.println("get lock failed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            /*
            * lock.isHeldByCurrentThread()的作用是查询当前线程是否保持此锁定
            * 和lock.hasQueueThread()不同的地方是：
            * lock.hasQueueThread(Thread thread)的作用是判断当前线程是否处于等待lock的状态。
            * */
            if (lock.isHeldByCurrentThread())lock.unlock();
        }
    }

    public static void main(String[] args) {
        TimeLock tl=new TimeLock();
        Thread t1=new Thread(tl);
        Thread t2=new Thread(tl);
        t1.start();t2.start();
    }
}
