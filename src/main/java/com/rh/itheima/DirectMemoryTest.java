package com.rh.itheima;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 直接内存申请与释放案例
 * 直接内存不受JVM管理
 */
public class DirectMemoryTest {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 1024);
        System.out.println("分配直接内存。。。");
        System.in.read();
        System.out.println("开始释放直接内存。。。");
        buffer = null;
        System.gc();    //显示的垃圾回收，Full GC  -XX:+DisableExplicitGC  禁用显示GC
        System.in.read();
    }
}
