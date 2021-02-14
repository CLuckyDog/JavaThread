package com.rh.bilibili.test;

import lombok.extern.slf4j.Slf4j;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * 简单模拟两个人泡茶的程序
 * 一个人洗水壶，烧水
 * 一个人洗茶壶，茶杯，拿茶叶
 * 最后交给其中一个人去泡茶
 */
@Slf4j(topic = "c.MakeTea")
public class MakeTea {

    public static void main(String[] args) {

        Thread person1 = new Thread(()->{
            log.debug("洗水壶......");
            sleep(1);
            log.debug("烧水......");
            sleep(15);
        },"二哈");

        Thread person2 = new Thread(()->{
            log.debug("洗茶壶......");
            sleep(1);
            log.debug("洗茶杯......");
            sleep(2);
            log.debug("泡茶......");
            sleep(1);
            //等待水烧开，泡茶
            log.debug("等待水烧开就可以泡茶了......");
            try {
                person1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("水烧开了，可以泡茶了......");
        },"我");

        person1.start();
        person2.start();
    }

}
