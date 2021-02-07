package com.rh.design_thread.chapter_three.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/10/5.
 */
public class ThreadPoolDemo {
    public static class MyTask implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+":Thread ID:"+Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            MyTask task=new MyTask();
            /*
            * newFixedThreadPool可控制线程最大并发数，当线程池中的线程数达到其设定大小时，
            * 其余新创建的线程会在LinkedBlockingQueue队列中等待。当线程池中的某个线程失败而终止时，
            * 新的线程会代替它执行剩下的任务。线程池中的线程只有在显式调用shutdown函数时才会退出线程池
            * */
            ExecutorService es= Executors.newFixedThreadPool(5);
            /*newCachedThreadPool：创建可缓存线程池，当线程池中的线程空闲时间超过60s，便会终止该空闲线程并从缓存线程池中移除*/
//            ExecutorService es= Executors.newCachedThreadPool();
            for (int i=0;i<10;i++){
                es.submit(task);
            }
        }
    }
}
