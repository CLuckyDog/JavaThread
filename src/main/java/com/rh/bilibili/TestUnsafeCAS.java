package com.rh.bilibili;

import com.rh.bilibili.utils.UnsafeAccessor;
import sun.misc.Unsafe;

public class TestUnsafeCAS {
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

    public static void main(String[] args) throws InterruptedException {
        Person person = new Person();
        new Thread(()->{
            int i=0;
            for (i = 0; i < 10000; i++) {
                while (true){
                    //获取value的操作，一定要放在while里面，否则可能导致死循环
//                    int pre = person.value;
                    //另一种CAS用法，基本数据类型变量不需要添加volatile修饰
                    int pre = UNSAFE.getIntVolatile(person, numOffset);
                    int nextVal = pre +1;
                    System.out.println("while aaa");
                    if (UNSAFE.compareAndSwapInt(person, numOffset, pre, nextVal)){
                        System.out.println("====="+nextVal);
                        break;
                    }
                }
            }
            System.out.println("++++"+i);
//            System.out.println(Thread.currentThread().getName()+"###"+person.value);
            System.out.println(Thread.currentThread().getName()+"###"+UNSAFE.getIntVolatile(person, numOffset));
        }).start();

        new Thread(()->{
            int i=0;
            for (i = 0; i < 10000; i++) {
                while (true){
                    //获取value的操作，一定要放在while里面，否则可能导致死循环
//                    int pre = person.value;
                    //另一种CAS用法，基本数据类型变量不需要添加volatile修饰
                    int pre = UNSAFE.getIntVolatile(person, numOffset);
                    int nextVal = pre +1;
                    System.out.println("while bbb");
                    if (UNSAFE.compareAndSwapInt(person, numOffset, pre, nextVal)){
                        System.out.println(">>>>>"+nextVal);
                        break;
                    }
                }
            }
            System.out.println("----"+i);
//            System.out.println(Thread.currentThread().getName()+"@@@"+person.value);
            System.out.println(Thread.currentThread().getName()+"@@@"+UNSAFE.getIntVolatile(person, numOffset));
        }).start();
//        Thread.sleep(1000);

    }

}
