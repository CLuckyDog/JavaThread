package com.rh.chapter_two;

/**
 * 线程优先级示例代码
 * synchronized (PriorityDemo.class)
 * Java规定，任何线程执行同步方法、同步代码块之前，必须先获取对应的监视器。
 * 这里的监视器是类本身
 * 当监视器相同时，只能同步执行，当监视器不同是，可以并发执行
 * https://www.iteye.com/blog/wangym-1265973
 * https://www.jianshu.com/p/4c1ed2048985
 */
public class PriorityDemo {

    public static class HightPriority extends Thread {
        static int count = 0;

        @Override
        public void run() {

            synchronized (PriorityDemo.class) {
                while (true) {
                    count++;
//                    System.out.println("HightPriority count:"+count);
                    if (count > 10000000) {
                        System.out.println("HightPriority is complete!");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriority extends Thread {
        static int count = 0;

        @Override
        public void run() {
            synchronized (PriorityDemo.class) {
                while (true) {
                    count++;
//                    System.out.println("LowPriority count:"+count);
                    if (count > 10000000) {
                        System.out.println("LowPriority is complete!");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        HightPriority high = new HightPriority();
        LowPriority low = new LowPriority();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        low.start();
        high.start();
    }
}
