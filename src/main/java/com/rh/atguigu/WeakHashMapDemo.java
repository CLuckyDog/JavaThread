package com.rh.atguigu;

import java.util.WeakHashMap;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: pzj
 * \* Date: 2021/10/14
 * \* Time: 15:04
 * \* Description:
 * \
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        WeakHashMap<Integer, String> weakHashMap = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "xxxx";
         weakHashMap.put(key,value);
        System.out.println(weakHashMap);

        key = null;

        System.gc();
        System.out.println(weakHashMap + "    " + weakHashMap.size());
    }
}