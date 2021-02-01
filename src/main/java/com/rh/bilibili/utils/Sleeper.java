package com.rh.bilibili.utils;

import java.util.concurrent.TimeUnit;

public class Sleeper {

    public static void sleep(int seconds){
        try {
            //用时1分钟
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
