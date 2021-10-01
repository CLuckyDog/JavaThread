package com.rh.itheima;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WeakReferenceTest {
    private final static int  _4MB = 1024*1024*4;
    public static void main(String[] args) {
        //list --> SoftReference --> byte[]
        List<WeakReference<byte[]>> list = new ArrayList<>();

        //引用队列
        ReferenceQueue<byte[]> queue = new ReferenceQueue<>();

        for (int i = 0; i < 15; i++) {
            //不关联引用队列
            WeakReference<byte[]> ref = new WeakReference<>(new byte[_4MB]);
            list.add(ref);
            //关联引用队列
//            WeakReference<byte[]> ref = new WeakReference<>(new byte[_4MB],queue);
            for (WeakReference<byte[]> reference : list) {
                System.out.println(reference.get());
            }
            System.out.println();
        }

        System.out.println("循环结束======");
    }
}
