package com.rh.atguigu;

import java.util.ArrayList;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: pzj
 * \* Date: 2021/10/14
 * \* Time: 15:39
 * \* Description:
 *                  -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 *                  OutOfMemoryError: GC overhead limit exceeded
 * \
 */
public class OOMError1 {
    public static void main(String[] args) {
        int i =0;
        ArrayList<String> list = new ArrayList<>();
        try {
            while (true){
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            System.out.println("***********   i: "+i);
            e.printStackTrace();
            throw e;
        }

    }
}