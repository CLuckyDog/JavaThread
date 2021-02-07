package com.rh.design_thread.chapter_two;

public class AccountingVol implements Runnable{

    static AccountingVol instance=new AccountingVol();
    static volatile int i=0;
    /*
     * 这里加sync保证了数据的准确性,两种sync使用方式效果一样
     * 添加它后就保证了每次输出 20000
     * */
//    public static void increase(){
//        synchronized (instance){
//            i++;
//        }
//    }
    public static synchronized void increase(){
            i++;
    }

    @Override
    public void run() {
        for (int j=0;j<10000;j++){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(instance);
        Thread t2=new Thread(instance);
        t1.start();t2.start();
        /*
        * 这里的join是必要的，否则输出一直未0，或者很小的数字
        * 因为加了join，main线程才会等待t1，和t2执行完，再进行输出
        * */
        t2.join();t1.join();
        System.out.println("i="+i);
    }
}
