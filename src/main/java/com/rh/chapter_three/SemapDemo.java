package com.rh.chapter_three;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2019/10/3.
 *
 * 信号量实例代码
 */
public class SemapDemo implements Runnable {
    /*
    * 定义5个许可证
    * */
    final Semaphore semp=new Semaphore(5);

    @Override
    public void run() {
        try {
            /*
            * 线程获取许可证，这里不再获取锁
            * */
            semp.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+":done!");
            /*
            * 线程释放许可证
            * */
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*
        * 创建一个线程池
        * */
        ExecutorService exec= Executors.newFixedThreadPool(20);
        final SemapDemo demo=new SemapDemo();
        for (int i=0;i<20;i++){
            exec.submit(demo);
        }
    }
}
