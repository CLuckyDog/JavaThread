package com.rh.design_thread.chapter_two;

/**
 * 守护线程Daemon 示例代码
 */
public class DaemonDemo {

    public static class DaemonT extends Thread{
        @Override
        public void run() {
            while (true){
                System.out.println("I am alive !");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t=new DaemonT();
        /*
        * 将线程t设置成守护线程
        * 这行设置必须在start前设置，否则报错 java.lang.IllegalThreadStateException
        * 但是，程序正常运行，被当做用户线程
        */
        t.setDaemon(true);
        t.start();
        /*
        * 由于t被设置成守护线程，系统中只有main线程为用户线程
        * 因此，在main函数sleep休眠2秒后，退出。
        */
        Thread.sleep(2000);
    }
}
