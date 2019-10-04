package com.rh.chapter_three;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/10/3.
 * CountDownLatch  倒计时数器，多线程并发控制工具类
 */
public class CountDownLatchDemo implements Runnable {
    /*计数数量为10，表示需要有10个线程完成任务，等待在CountDownLatch上的线程才能继续执行*/
    static final CountDownLatch end=new CountDownLatch(10);
    static final CountDownLatchDemo demo=new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            /*模拟检查任务！*/
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println(Thread.currentThread().getId()+"check complete!");
            /*通知有一个线程完成了任务，倒计时器减去1*/
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            /*启动10个线程*/
            exec.submit(demo);
        }
        /*等待检查*/
        end.await();
        /*发射火箭*/
        System.out.println("Fire!");
        exec.shutdown();

    }
}
