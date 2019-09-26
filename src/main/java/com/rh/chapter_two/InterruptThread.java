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
                        /**
                         * 因为 sleep 发生异常后，会清除interrupt标记，
                         * 所以，在异常捕获里，重新添加标记
                         */
                        System.out.println("Interrupt When Sleep!");
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };

        t.start();
        /**
         * sleep方法是为了让线程运行到第19行的sleep方法处，然后
         * 执行interrupt方法时，制造InterruptedException
         */
        Thread.sleep(2000);
        t.interrupt();
    }
}
