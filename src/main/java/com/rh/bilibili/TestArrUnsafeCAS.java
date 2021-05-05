package com.rh.bilibili;

import com.rh.bilibili.utils.UnsafeAccessor;
import sun.misc.Unsafe;

public class TestArrUnsafeCAS {
    private static final long valueOffset;
    private static final long numOffset;
    static final Unsafe UNSAFE;
    static {
        UNSAFE= UnsafeAccessor.getUnsafe();
        try {
            valueOffset=UNSAFE.objectFieldOffset(Person.class.getDeclaredField("value"));
            numOffset=UNSAFE.objectFieldOffset(Person.class.getDeclaredField("num"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Person person = new Person();
        //获取数组中存储的对象的对象头大小
        int arrIndex = UNSAFE.arrayIndexScale(String[].class);
        //数组中第一个元素的起始位置
        int baseOffset = UNSAFE.arrayBaseOffset(String[].class);
        //输出数组中index为2的位置上的内容
        System.out.println(UNSAFE.getObject(person.table,baseOffset+1*arrIndex));
    }
}
