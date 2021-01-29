package com.rh.bilibili.test;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/1/26
 * \* Time: 9:56
 * \* Description:
 * \
 */
public class TestDebugThread {
    public static void main(String[] args) {
        int x=9;
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                int rel=method1(x);
                System.out.println(rel);
            }
        });
        t1.setName("t1");
        t1.start();
        int rel=method1(x);
        System.out.println(rel);
    }

    private static int method1(int x) {
        int y=x+9;
        Integer m=method2(y);

        return m;
    }

    private static Integer method2(int y) {
        Integer m=new Integer(y);
        return m;
    }
}