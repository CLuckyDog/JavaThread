package com.rh.vedio.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j(topic = "c.FutureTaskTest")
public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task= new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running....");
                Thread.sleep(1000);
                return 200;
            }
        });

        Thread t1=new Thread(task,"t1");
        t1.start();

        log.debug("{}",task.get());
    }
}
