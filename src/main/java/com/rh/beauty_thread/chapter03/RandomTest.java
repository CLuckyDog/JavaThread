package com.rh.beauty_thread.chapter03;   // 解决package

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: RandomTest
 * @Description:
 * @author:pzj
 * @date: 2021/3/26 17:30
 */
@Slf4j(topic = "c.RandomTest")
public class RandomTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {

        }, "t1");

//        t1.start();

//        Random random=new Random();
//        List<Thread> lt=new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Thread thread = new Thread(() -> {
//                System.out.println(random.nextInt(10000));
//            }, "t" + i);
//            lt.add(thread);
//        }
//
//        lt.forEach(t->{t.start();});


        List<Thread> lt=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                ThreadLocalRandom tlr=ThreadLocalRandom.current();
                System.out.println(tlr.nextInt(10000));
            }, "t" + i);
            lt.add(thread);
        }

        lt.forEach(t->{t.start();});
    }

}
