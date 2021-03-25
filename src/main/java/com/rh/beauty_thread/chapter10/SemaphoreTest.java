package com.rh.beauty_thread.chapter10;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/3/23
 * \* Time: 14:25
 * \* Description: 信号量测试代码
 * \
 */
@Slf4j(topic = "c.SemaphoreTest")
public class SemaphoreTest {
    //创建一个Semaphore实例
    private static Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //将任务添加到线程池中
        executorService.submit(()->{
            log.debug(Thread.currentThread()+"over!");
            semaphore.release();
        });

        executorService.submit(()->{
            log.debug(Thread.currentThread()+"over!");
            semaphore.release();
        });

        semaphore.acquire(2);
        log.debug("all child thread over!");

        //关闭线程池
        executorService.shutdown();
    }

}