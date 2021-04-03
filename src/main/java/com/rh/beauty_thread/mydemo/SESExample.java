package com.rh.beauty_thread.mydemo;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SESExample {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
        Random random = new Random();

//        pool.scheduleWithFixedDelay(() -> {
//            int i = random.nextInt(100);
//            System.out.println(i);
//            if (i % 5 == 0) {
//                throw new RuntimeException("Exception triggered! HAHA");
//            }
//        }, 1, 3, TimeUnit.SECONDS);

        pool.scheduleAtFixedRate(() -> {
            try {
                int i = random.nextInt(100);
                System.out.println(i);
                if (i % 5 == 0) {
                    throw new RuntimeException("Exception triggered! HAHA");
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }, 1, 3, TimeUnit.SECONDS);

//        pool.shutdown();
    }
}

