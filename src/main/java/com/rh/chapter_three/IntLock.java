package com.rh.chapter_three;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2019/10/1.
 * 重入锁的中断响应示例代码
 *
 * 容易看出，t1和t2处于死锁状态，互相等待，所以，程序无法停止
 * 此时，在main中，t2中断了，在run中，t2线程也就放弃了等待lock1的任务
 * 所以，t1，完成了任务。t2，直接响应中断退出了。
 *
 * 现实中的例子：
 *      比如，你和朋友约好一起去打球，如果你等了半个小时，朋友还没有到，突然接到朋友的一个电话
 *      说由于突发情况，不能如约了。那么你一定扫兴的打道回府了。
 */
public class IntLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public IntLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                /*
                * 该方法的作用
                * 对锁的请求，统一使用lockInterruptibly()方法
                * 它可以对中断进行响应的锁申请动作，即在等待锁的过程中，可以响应中断。
                * 如果在等待锁的过程中发生中断，则报出异常，停止等待。
                * */
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread().getId()+":线程获取lock1锁");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread().getId()+":线程获取lock2锁");
            } else {
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread().getId()+":线程获取lock2锁");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread().getId()+":线程获取lock1锁");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId()+":线程退出");
        }

    }

    public static void main(String[] args) throws InterruptedException {
        IntLock r1=new IntLock(1);
        IntLock r2=new IntLock(2);
        Thread t1=new Thread(r1);
        Thread t2=new Thread(r2);
        t1.start();t2.start();
        Thread.sleep(1000);
        t2.interrupt();
    }
}
