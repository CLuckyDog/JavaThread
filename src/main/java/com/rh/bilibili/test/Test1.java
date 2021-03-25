package com.rh.bilibili.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {
    public static void main(String[] args) {

        AtomicInteger integer = new AtomicInteger(0);
        int c =-1;
        c=integer.getAndIncrement();
        System.out.println(c);
        System.out.println(integer.get());
        System.out.println("----------------1---------------");
        System.out.println(++c == 0);
//        System.out.println(c++ == 0);
        System.out.println(c);
        System.out.println("----------------2---------------");
        int oldCap = 11;
        System.out.println("(oldCap >> 1)  :"+(oldCap >> 1));
        System.out.println("(oldCap < 64)  :"+(oldCap < 64));
        System.out.println("after (oldCap >> 1)  :"+oldCap);
        System.out.println("after (oldCap < 64)  :"+oldCap);
        int newCap = oldCap + ((oldCap < 64) ?(oldCap + 2) : (oldCap >> 1));
        System.out.println("newCap:"+newCap);
        System.out.println("----------------3---------------");
        int k = 3;
        System.out.println(k >>> 1);
        System.out.println(k >> 1);
       int COUNT_BITS = Integer.SIZE - 3;
        /**
         * 线程最大个数（假设是32位系统，则低位29位）：    0001 1111 1111 1111 1111 1111 1111 1111
         * 64位系统就是：0001 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111 1111
         * 1110 0000 0000 0000 0000 0000 0000 0000
         * 0001 1111 1111 1111 1111 1111 1111 1111
         */
        int CAPACITY   = (1 << COUNT_BITS) - 1;

        //接受新任务并且处理阻塞队列里的任务。
         final int RUNNING    = -1 << COUNT_BITS;
        //拒绝新任务但是处理阻塞队列里的任务。
         final int SHUTDOWN   =  0 << COUNT_BITS;
        //拒绝新任务并且抛弃阻塞队列里的任务，同时会中断正在处理的任务。
         final int STOP       =  1 << COUNT_BITS;
        //所有任务都执行完（包含阻塞队列里面的任务）后当前线程池活动线程
        //数为0 ， 将要调用terminated 方法。
         final int TIDYING    =  2 << COUNT_BITS;
        //终止状态。terminated 方法调用完成以后的状态。
         final int TERMINATED =  3 << COUNT_BITS;
        System.out.println(CAPACITY);
        System.out.println(~CAPACITY);
        System.out.println(Integer.toBinaryString(~CAPACITY));
        System.out.println(Integer.toBinaryString(-15));
        System.out.println(~-15);
        System.out.println(Integer.toBinaryString(~-15));
        System.out.println("----------"+Integer.toBinaryString(RUNNING & ~CAPACITY));
        System.out.println("----------------4---------------");
        String requestIp = "202.102.117.16";
        String userIP="127.0.0.1";

        String patternString = "(([0,1]?\\d?\\d|2[0-4]\\d|25[0-5])\\.){3}([0,1]?\\d?\\d|2[0-4]\\d|25[0-5])";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher1 = pattern.matcher(requestIp);
        Matcher matcher2 = pattern.matcher(userIP);
        if (!(matcher1.matches()&&matcher2.matches()) && !userIP.contains(requestIp)){
            System.out.println(requestIp);
            System.out.println(userIP);
        }else{
            System.out.println("IP 格式错误！");
        }


    }


     private static String name;

    static class X{

        public String getName(){
            name="x";
            return name;
        }
    }
}

