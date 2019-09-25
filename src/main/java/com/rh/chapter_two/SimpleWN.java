package com.rh.chapter_two;

public class SimpleWN {
    final static Object object=new Object();

    public static class T1 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                System.out.println(System.currentTimeMillis()+":T1 start !");
                try {
                    System.out.println(System.currentTimeMillis()+":T1 wait for object !");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+":T1 end !");

            }
        }
    }

    public static class T2 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                System.out.println(System.currentTimeMillis()+":T2 start ! notify one thread");
                /**
                 * 这个执行完之后，T2线程并没有释放object锁，而是在synchronized走完之后，才释放锁
                 * 也就是说，notify方法执行了，并不代表 资源锁 就被释放了
                 * wait方法同理。
                 */
                object.notify();
                System.out.println(System.currentTimeMillis()+":T2 end !");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1=new T1();
        Thread t2=new T2();
        t1.start();
        t2.start();

    }
}
