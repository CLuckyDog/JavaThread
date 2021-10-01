package com.rh.itheima;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 软引用的使用案例，并配合引用队列，进行软引用的回收
 * -Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class SoftReferenceTest {
    private final static int  _4MB = 1024*1024*4;
    public static void main(String[] args) {
        //list --> SoftReference --> byte[]
        List<SoftReference<byte[]>> list = new ArrayList<>();

        //引用队列
        ReferenceQueue<byte[]> queue = new ReferenceQueue<>();

        for (int i = 0; i < 5; i++) {
            //不关联引用队列
//            SoftReference<byte[]> ref = new SoftReference<>(new byte[_4MB]);
            //关联引用队列
            SoftReference<byte[]> ref = new SoftReference<>(new byte[_4MB],queue);
            System.out.println(ref.get());
            list.add(ref);
            System.out.println(list.size());
        }
        //从队列中获取无用的  软引用对象  并从list中移除
        Reference<? extends byte[]> poll = queue.poll();
        while (poll != null){
            list.remove(poll);
            poll = queue.poll();
        }

        System.out.println("循环结束："+list.size());
        for (SoftReference<byte[]> softReference : list) {
            System.out.println(softReference.get());
        }
    }
}
