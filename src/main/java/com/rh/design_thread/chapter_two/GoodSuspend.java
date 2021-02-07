package com.rh.design_thread.chapter_two;

/**
 * 线程suspend的使用优化案例
 */
public class GoodSuspend {
    public static Object u=new Object();

    public static class ChangeObjectThread extends Thread{
        volatile boolean suspendme=false;

        public void suspendMe(){
            suspendme=true;
        }

        public void resumeMe(){
            suspendme=false;
            /**
             * 这里的this指当前类 GoodSuspend 实例
             * 参考：https://www.jianshu.com/p/4c1ed2048985
             */
            synchronized (this){
                notify();
            }
        }

        @Override
        public void run() {
            while (true){

                synchronized (this){
                    while (suspendme)
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
                synchronized (u){
                    System.out.println("in ChangeObjectThread");
                }
                /**
                 * yield 即 “谦让”，也是 Thread 类的方法。它让掉当前线程 CPU 的时间片
                 * 使正在运行中的线程重新变成就绪状态，并重新竞争 CPU 的调度权
                 * 它可能会获取到，也有可能被其他线程获取到
                 */
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (u){
                    System.out.println("in ReadObjectThread");
                }
                Thread.yield();
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1=new ChangeObjectThread();
        ReadObjectThread t2=new ReadObjectThread();

        t1.start();
        t2.start();

        Thread.sleep(1000);
        t1.suspendMe();
        System.out.println("suspend t1 2 sec");
        Thread.sleep(2000);
        System.out.println("resume t1");
        t1.resumeMe();
    }
}
