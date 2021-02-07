package com.rh.design_thread.chapter_two;

/**
 * join 方法的示例代码
 */
public class JoinMain {
    public volatile static int i=0;
    public static class AddThread extends Thread{
        @Override
        public void run() {
            for(i=0;i<1000000;i++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread at=new AddThread();
        at.start();
        /**
         * 从运行结果可以看出
         * 是main线程等待AddThread线程完成在执行后续代码
         * 所以，at.join()的含义，就是，at线程加入。
         * 哪个线程调用了join方法，那么，哪个线程的程序就要被执行，直到执行完，后续代码才执行。
         */
        at.join();
        System.out.println(i);
    }
}
