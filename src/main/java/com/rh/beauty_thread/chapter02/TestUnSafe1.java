package com.rh.beauty_thread.chapter02;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/2/4
 * \* Time: 10:27
 * \* Description:
 * \
 */
@Slf4j(topic = "c.TestUnSafe1")
public class TestUnSafe1 {
    //获取Unsafe的实例
    static final Unsafe unsafe;

    //记录变量state在类TestUnSafe中的偏移值
    static final long stateOffset;

    //变量
    private volatile long state=0;

    static {
        try {
            //使用反射获取Unsafe的成员变量theUnsafe
            Field filed =Unsafe.class.getDeclaredField("theUnsafe");

            //设置成可存取
            filed.setAccessible(true);

            unsafe = (Unsafe) filed.get(null);

            stateOffset=unsafe.objectFieldOffset(TestUnSafe1.class.getDeclaredField("state"));
            log.debug("stateOffset:"+stateOffset);
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        //创建实例，并且设置state值为1
        TestUnSafe1 test = new TestUnSafe1();

        boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        log.debug(String.valueOf(success));

    }

}