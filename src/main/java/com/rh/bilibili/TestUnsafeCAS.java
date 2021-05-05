package com.rh.bilibili;

import com.rh.bilibili.utils.UnsafeAccessor;
import sun.misc.Unsafe;

public class TestUnsafeCAS {
    private static final long valueOffset;
    static final Unsafe UNSAFE;
    static {
        UNSAFE= UnsafeAccessor.getUnsafe();
        try {
            valueOffset=UNSAFE.objectFieldOffset(Person.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Person person = new Person();
        new Thread(()->{
            int i=0;
            for (i = 0; i < 10000; i++) {
                while (true){
                    int pre = person.value;
                    int nextVal = pre +1;
                    System.out.println("while aaa");
                    if (UNSAFE.compareAndSwapInt(person, valueOffset, pre, nextVal)){
                        System.out.println("====="+nextVal);
                        break;
                    }
                }
            }
            System.out.println("++++"+i);
            System.out.println(Thread.currentThread().getName()+"###"+person.value);
        }).start();

        new Thread(()->{
            int i=0;
            for (i = 0; i < 10000; i++) {
                while (true){
                    //获取value的操作，一定要放在while里面，否则可能导致死循环
                    int pre = person.value;
                    int nextVal = pre +1;
                    System.out.println("while bbb");
                    if (UNSAFE.compareAndSwapInt(person, valueOffset, pre, nextVal)){
                        System.out.println(">>>>>"+nextVal);
                        break;
                    }
                }
            }
            System.out.println("----"+i);
            System.out.println(Thread.currentThread().getName()+"@@@"+person.value);
        }).start();
//        Thread.sleep(1000);

    }

}
