package com.rh.chapter_three;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2019/10/1.
 * 不传时间参数的tryLock方法
 * 锁被占用的话，立即返回FALSE，不等待
 */
public class TryLock implements Runnable {
    public static ReentrantLock lock1=new ReentrantLock();
    public static ReentrantLock lock2=new ReentrantLock();
    int lock;
    public TryLock(int lock){
        this.lock=lock;
    }

    @Override
    public void run() {
        if (lock==1){
            System.out.println("lock = 1");
            while (true){
                if (lock1.tryLock()){
                    try{
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {

                        }
                        if (lock2.tryLock()){
                            try{
                                System.out.println(Thread.currentThread().getId()+":xMy Job done!");
                            }finally {
                                lock2.unlock();
                            }
                        }
                    }finally {
                        lock1.unlock();
                    }
                }
            }
        }else{
            System.out.println("lock = 2");
            while (true){
                if (lock2.tryLock()){
                    try{
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {

                        }
                        if (lock1.tryLock()){
                            try{
                                System.out.println(Thread.currentThread().getId()+":yMy Job done!");
                            }finally {
                                lock1.unlock();
                            }
                        }
                    }finally {
                        lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TryLock r1=new TryLock(1);
        TryLock r2=new TryLock(2);
        Thread t1=new Thread(r1);
        Thread t2=new Thread(r2);
        t1.start();t2.start();
    }
}
