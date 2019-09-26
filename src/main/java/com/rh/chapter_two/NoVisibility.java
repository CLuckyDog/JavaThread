package com.rh.chapter_two;

/**
 * volatile 可见性的使用案例
 */
public class NoVisibility {

    private static volatile boolean ready;
    private static int number;

    private static class ReaderThread extends Thread{
        @Override
        public void run() {
            while (!ready);
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        /**
         * 这里的sleep是为了让run方法运行到while处，从而验证volatile的作用。
         */
        Thread.sleep(1000);
        number=42;
        ready=true;
        Thread.sleep(10000);
    }

}
