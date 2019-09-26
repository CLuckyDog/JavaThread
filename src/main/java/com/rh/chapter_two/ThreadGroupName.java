package com.rh.chapter_two;

/**
 * 线程组的简单示例代码
 */
public class ThreadGroupName implements Runnable{
    @Override
    public void run() {
        String groupAndName=Thread.currentThread().getThreadGroup().getName()
        +"-"+Thread.currentThread().getName();

        while (true){
            System.out.println("I am"+groupAndName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        /*创建一个名为PrintGroup的线程组*/
        ThreadGroup tg=new ThreadGroup("PrintGroup");
        /*创建两个线程 T1,T2 并把他们添加到线程组中*/
        Thread t1=new Thread(tg,new ThreadGroupName(),"T1");
        Thread t2=new Thread(tg,new ThreadGroupName(),"T2");

        t1.start();
        t2.start();
        /*activeCount 这个方法可以获得活动线程的总数，但这个值只是一个估计值，因为线程是动态的*/
        System.out.println(tg.activeCount());
        /*打印这个线程组中所有的线程信息，有利于调试*/
        tg.list();
    }
}
