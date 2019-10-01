package com.rh.chapter_two;

/**
 * Created by Administrator on 2019/9/30.
 */
public class BadLockOnInteger implements Runnable{
    /*
    * 不可以给进行i++操作的包装类加锁
    * */
    public static Integer i=0;
    static BadLockOnInteger instance=new BadLockOnInteger();

    @Override
    public void run() {
        for (int j=0;j<1000000;j++){
            synchronized (instance){
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(instance);
        Thread t2=new Thread(instance);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println("i="+i);
    }
}
