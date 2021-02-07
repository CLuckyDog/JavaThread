package com.rh.design_thread.chapter_two;

/**
 * volatile可见性的实例代码
 */
public class VolatileIntThread {
    /**
     * 注意，volatile不能等价于 sync。它无法保证一些复合操作的原子性。
     */
    public static volatile int i=0;

    public static class PlusTask implements Runnable{
        @Override
        public void run() {
            for(int k=0;k<10000;k++)i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads=new Thread[10];
        for(int i=0;i<10;i++){
            threads[i]=new Thread(new PlusTask());
            threads[i].start();
        }

        for (int i=0;i<10;i++){
            threads[i].join();
        }

        System.out.println(i);
    }
}
