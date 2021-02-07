package com.rh.design_thread.chapter_two;

/**
 * 线程挂起 suspend 方法的错误使用案例
 */
public class BadSuspend {

    public static Object u=new Object();
    static ChangeObjectThread t1=new ChangeObjectThread("t1");
    static ChangeObjectThread t2=new ChangeObjectThread("main");


    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            System.out.println("create "+name);
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("in "+getName());
                Thread.currentThread().suspend();
            }
            System.out.println(getName()+" end !");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.resume();
        t2.resume();
        System.out.println(System.currentTimeMillis()+"   t2.resume() end!");
        t1.join();
        t2.join();
    }
}
