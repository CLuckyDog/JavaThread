package com.rh.bilibili.chapter08;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestThreadPoolExecuters
 * @Description:
 * @author: pzj
 * @date: 2021/3/14 11:50
 */
@Slf4j(topic = "c.TestThreadPoolExecutors")
public class TestThreadPoolExecutors {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t=new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"mypool_t"+t.getAndIncrement());
            }
        });

        pool.execute(()->{
            while (true)
            log.debug("1");
        });

        pool.execute(()->{
            log.debug("2");
        });


        pool.execute(()->{
            log.debug("3");
        });

        pool.shutdownNow();

    }
}