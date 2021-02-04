package com.rh.beauty_thread.chapter04;

import java.util.concurrent.atomic.AtomicLong;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/2/4
 * \* Time: 11:13
 * \* Description:
 * \
 */
public class AtomicLongTest {
    //创建Long型原子计数器
    private static AtomicLong atomicLong = new AtomicLong();
    //创建数据源
    private static Integer[] arrayOne = new Integer[]{0,1,2,3,0,5,6,0,56,0};
    private static Integer[] arrayTwo = new Integer[]{0,1,2,3,0,5,6,0,56,0};

    public static void main(String[] args) throws InterruptedException {
        //线程one统计数组arrayOne中的0的个数
        Thread threadOne= new Thread(()->{
            int size = arrayOne.length;
            for (int i =0; i <size;i++){
                if (arrayOne[i].intValue() == 0){
                    atomicLong.incrementAndGet();
                }
            }
        },"threadOne");

        //线程two统计数组arrayTwo中的0的个数
        Thread threadTwo= new Thread(()->{
            int size = arrayTwo.length;
            for (int i =0; i <size;i++){
                if (arrayTwo[i].intValue() == 0){
                    atomicLong.incrementAndGet();
                }
            }
        },"threadTwo");

        threadOne.start();
        threadTwo.start();

        threadOne.join();
        threadTwo.join();

        System.out.println("count 0 :"+atomicLong);
    }
}