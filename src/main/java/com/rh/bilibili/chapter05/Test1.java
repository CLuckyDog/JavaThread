package com.rh.bilibili.chapter05;   // 解决package

import lombok.extern.slf4j.Slf4j;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: Test1
 * @Description:
 * @author: pzj
 * @date: 2021/2/16 17:32
 */
@Slf4j(topic = "c.Test1")
public class Test1 {
    static boolean flag = true;
    static byte[] b = new byte[0];

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {

            while (flag) {
                synchronized (b) {
                }
            }

        }, "t1");
        t1.start();

        sleep(0.5);
        flag = false;

    }
}