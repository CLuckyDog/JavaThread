package com.rh.beauty_thread.chapter02;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/2/4
 * \* Time: 10:15
 * \* Description:
 *                  java.lang.SecurityException: Unsafe
 * \
 */
@Slf4j(topic = "c.TestUnSafe")
public class TestUnSafe {
    //获取Unsafe的实例
    static final Unsafe unsafe= Unsafe.getUnsafe();

    //记录变量state在类TestUnSafe中的偏移值
    static final long stateOffset;

    //变量  A_PRODUCTPAYLOCK
    private volatile long state=0;

    static {
        //获取state变量在类TestUnSafe中的偏移值
        try {
            stateOffset=unsafe.objectFieldOffset(TestUnSafe.class.getDeclaredField("state"));
            log.debug(String.valueOf(stateOffset));
        } catch (NoSuchFieldException e) {
            log.debug(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        //创建实例，并且设置state值为1
        TestUnSafe test = new TestUnSafe();

        boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        log.debug(String.valueOf(success));

    }
}