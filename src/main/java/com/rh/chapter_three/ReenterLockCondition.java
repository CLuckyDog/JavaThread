package com.rh.chapter_three;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2019/10/3.
 *
 * 重入锁的搭档  Condition
 *
 * 当线程使用Condition.await()时，要求线程持有相关的重入锁，在Condition.await()调用后，这个线程会释放该锁。
 *
 */
public class ReenterLockCondition implements Runnable{
    public static ReentrantLock lock=new ReentrantLock();
    /*
    * 通过lock生成一个与之绑定的Condition对象
    * */
    public static Condition condition=lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            /*
            * 要求当前线程在Condition对象上进行等待，调用condition方法时，必须先获得锁
            * 在调用了该方法之后，当前线程会释放锁
            * */
            condition.await();
            System.out.println("Thread is going on!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition tl=new ReenterLockCondition();
        Thread t1=new Thread(tl);
        t1.start();
        Thread.sleep(2000);
        /*
        * 获取锁，才可以调用后面的condition.signal()方法。
        * 否则报错
        * */
        lock.lock();
        /*
        * 在signal()调用后，系统会从当前Condition对象的等待队列中，唤醒一个线程
        * 一旦线程被唤醒，它会重新尝试获得与之绑定的重入锁
        * 一旦成功获取，就可以继续执行。
        * 因此，signal()方法调用之后，一般需要释放相关锁，谦让给被唤醒的线程，让他可以继续执行
        * */
        condition.signal();
        /*
        * 虽然已经唤醒了t1，但是由于它无法重现获得锁，因为也就无法真正的继续执行
        * */
        lock.unlock();
    }
}
