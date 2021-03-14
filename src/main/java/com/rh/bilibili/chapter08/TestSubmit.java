package com.rh.bilibili.chapter08;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestSubmit
 * @Description:
 * @author: pzj
 * @date: 2021/3/14 17:30
 */
@Slf4j(topic = "c.TestSubmit")
public class TestSubmit {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        final Future<String> submit = pool.submit(() -> {
            log.debug("running....");
            Thread.sleep(1000);
            return null;
        });

        log.debug("{}",submit.get());

    }
}