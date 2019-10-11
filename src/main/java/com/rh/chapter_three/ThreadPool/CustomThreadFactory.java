package com.rh.chapter_three.ThreadPool;

import java.util.concurrent.*;

/**
 *  自定义ThreadFactory示例代码
 *  一，记录了线程的创建
 *  二，所有线程设置为守护线程，这样当main线程退出后，将会强制销毁线程池
 */
public class CustomThreadFactory {

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
    }
    public static void main(String[] args) throws InterruptedException {
        MyTask task=new MyTask();
        ExecutorService es=new ThreadPoolExecutor(
                5, 5, 0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t=new Thread(r);
                        t.setDaemon(true);
                        System.out.println("create "+t);
                        return t;
                    }
                }
        );
        for(int i=0;i<5;i++){
            es.submit(task);
        }
        Thread.sleep(2000);
    }
    
    
}
