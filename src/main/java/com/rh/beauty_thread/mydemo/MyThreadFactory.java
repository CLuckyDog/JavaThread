package com.rh.beauty_thread.mydemo;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/4/2
 * \* Time: 10:30
 * \* Description:
 * \
 */
public class MyThreadFactory implements ThreadFactory {

    private final AtomicInteger poolNumber = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;

    public MyThreadFactory(String namePrefix) {
        SecurityManager securityManager = System.getSecurityManager();
        group = (securityManager != null)?securityManager.getThreadGroup():Thread.currentThread().getThreadGroup();
        if (namePrefix ==null || namePrefix.isEmpty()){
            namePrefix = "myPool";
        }
        this.namePrefix = namePrefix+"-"+poolNumber.getAndIncrement()+"-thread-";
    }


    @Override
    public Thread newThread(Runnable r) {

        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()){
//            t.setDaemon(false);
            t.setDaemon(true);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY){
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}