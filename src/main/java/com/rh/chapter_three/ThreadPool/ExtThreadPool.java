package com.rh.chapter_three.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：pan_zhongjian
 * @version :   1.0
 * @date ：Created in 2019/10/10 17:15
 * @description: 扩展线程池示例代码
 * @modified By:
 */
public class ExtThreadPool {

    public static class MyTask implements Runnable{
        public String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("正在执行:Thread ID:"+Thread.currentThread().getId()+",Task Name="+name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /**
        * @Author: pan_zhongjian
        * @Description: 在默认的ThreadPoolExecutor实现中，提供了空的 beforeExecute，afterExecute ，terminated方法
         *              实际应用中，可以对其进行扩展来实现对线程池运行状态的跟踪，输出一些有用的信息。
         *              这个例子中，三个方法记录了一个任务的开始、结束和整个线程池的退出。
        * @DateTime: 2019/10/11 11:07
        * @Params: [args]
        * @Return void
        */
        ExecutorService es= new ThreadPoolExecutor(5,5,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行:"+((MyTask)r).name);
            }

            @Override

            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成:"+((MyTask)r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出！");
            }
        };
        for (int i=0;i<5;i++){
            MyTask task=new MyTask("TASK-GEYM-"+i);
            es.execute(task);
            Thread.sleep(100);
        }
        /*
        * 关闭线程池
        * 这是一个比较安全的方法，如果当前正有线程在执行，shutdown方法不会立即暴力终止任务，
        * 它会等待所有任务执行完成后，再关闭线程池，但是，它不会等待所有线程执行完成后再返回，
        * 因此，可以简单地理解成shutdown只是发送了一个关闭信号，但在shutdown方法执行后，
        * 这个线程池就不能在接收其他新的任务了。
        * */
        es.shutdown();

        /*
        * 获取可用的CPU数量。
        * */
        int x=Runtime.getRuntime().availableProcessors();
        System.out.println("availableProcessors:"+x);
    }

}
