package com.rh.beauty_thread.chapter11;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/3/25
 * \* Time: 10:54
 * \* Description:
 * \
 */
public class TestThreadPoolName {
    static ThreadPoolExecutor executorOne= new ThreadPoolExecutor(5,5,1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(),new MssThreadFactory("ASYN-ACCEPT-POOL"));

    static ThreadPoolExecutor executorTwo= new ThreadPoolExecutor(5,5,1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(),new MssThreadFactory("ASYN-PROCESS-POOL"));
    public static void main(String[] args) {
        //接受用户链接模块
        executorOne.execute(()->{
            System.out.println("接受用户链接线程");
            throw new NullPointerException();
        });

        //具体处理用户请求模块
        executorTwo.execute(()->{
            System.out.println("具体处理业务请求线程！");
        });

        executorTwo.shutdown();
        executorOne.shutdown();
    }
}

class MssThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    MssThreadFactory(String namePrefix) {
        SecurityManager manager = System.getSecurityManager();
        group=(manager != null) ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if (null == namePrefix || namePrefix.isEmpty()){
            namePrefix = "pool";
        }
        this.namePrefix = namePrefix+"-"+poolNumber.getAndIncrement()+"-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread( group,r,namePrefix + threadNumber.getAndIncrement(),0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}