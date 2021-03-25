package com.rh.beauty_thread.chapter11;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestSimpleDateFormatSafe
 * @Description:
 * @author:pzj
 * @date: 2021/3/25 10:13
 */
@Slf4j(topic = "c.TestSimpleDateFormatSafe")
public class TestSimpleDateFormatSafe {

    //创建threadLocal实例
    static ThreadLocal<DateFormat> safeSdf = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    //使用单例日期实例解析文本
                    log.debug(String.valueOf(safeSdf.get().parse("2017-12-13 10:10:11")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }finally {
                    //使用完记得清除，防止内存泄露
                    safeSdf.remove();
                }
            }, "t"+i);
            t.start();
        }
    }
}
