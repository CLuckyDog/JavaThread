package com.rh.design_thread.chapter_two;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/9/29.
 * 并发下的ArrayList
 *
 * 可能有三种结果。
 * 1，正常结果，2000000。
 * 2，报错。
 *          因为，ArrayList在扩容过程中，内部一致性被破坏
 *          但由于没有锁的保护，另外一个线程访问到了不一致的内部状态，导致出现越界问题。
 * 3，不报错，但是得到一个小于2000000的数值。
 *
 * 优化：加sync锁即可解决问题。扩容错误可以在创建时不传入10即可避免。
 */
public class ArrayListMultiThread {
//    static ArrayList<Integer> arrayList=new ArrayList<>(10);
    static ArrayList<Integer> arrayList=new ArrayList<>();

    public static class AddThread implements Runnable{
        @Override
        public void run() {
            synchronized (AddThread.class){
                for(int i=0;i<1000000;i++){
                    arrayList.add(i);
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new AddThread());
        Thread t2=new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(arrayList.size());
    }
}
