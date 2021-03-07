package com.rh.bilibili.chapter07;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestSDF
 * @Description:
 * @author: pzj
 * @date: 2021/3/7 10:46
 */
@Slf4j(topic = "c.TestSDF")
public class TestSDF {
    public static void main(String[] args) {
        DateTimeFormatter sdf =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
                new Thread(() -> {
                    final TemporalAccessor parse = sdf.parse("2021-03-07");
                    log.debug("{}",parse);
                }).start();
        }
    }

    private static void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            synchronized (sdf) {
                new Thread(() -> {
                    try {
                        log.debug("{}", sdf.parse("2021-03-07"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}