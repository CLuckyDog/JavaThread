package com.rh.bilibili.chapter06;   // 解决package

import com.rh.bilibili.utils.UnsafeAccessor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: MyUnsafeApply
 * @Description:
 * @author: pzj
 * @date: 2021/3/7 10:23
 */
@Slf4j(topic = "c.MyUnsafeApply")
public class MyUnsafeApply {
    public static void main(String[] args) {
        Account.demo(new MyAtomicInteger(10000));
    }
}

class MyAtomicInteger implements Account{
    private volatile int value;
    private static final long valueOffset;
    static final Unsafe UNSAFE;
    static {
        UNSAFE= UnsafeAccessor.getUnsafe();
        try {
            valueOffset=UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public void decrement(int amount){
        while (true){
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this,valueOffset,prev,next)) {
                break;
            }
        }
    }

    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }
}