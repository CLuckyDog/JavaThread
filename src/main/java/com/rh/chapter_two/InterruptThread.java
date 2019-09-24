package com.rh.chapter_two;

/**
 * 测试线程 interrupt 方法的使用
 */

public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(){
            @Override
            public void run() {
                while (true){

                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Interrupt");
                        break;
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupt When Sleep!");
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };

        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }
}
