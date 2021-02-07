package com.rh.design_thread.chapter_four;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/5 15:02
 * @description: 模拟哲学家吃饭的死锁情形
 * @modified By:
 */
public class DeadLock extends Thread {
    protected Object tool;
    static Object fork1=new Object();
    static Object fork2=new Object();
    
    public DeadLock(Object obj){
        this.tool=obj;
        if (tool == fork1){
            this.setName("哲学家A");
        }
        if (tool == fork2){
            this.setName("哲学家B");
        }
    }

    @Override
    public void run() {
        if (tool == fork1){
            synchronized (fork1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                synchronized (fork2){
                    System.out.println("哲学家A开始吃饭了！");
                }
            }
        }
        
        if (tool == fork2){
            synchronized (fork2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                synchronized (fork1){
                    System.out.println("哲学家B开始吃饭了！");
                }
            }
        }
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        DeadLock PhilosopherA=new DeadLock(fork1);
        DeadLock PhilosopherB=new DeadLock(fork2);

        PhilosopherA.start();
        PhilosopherB.start();
        Thread.sleep(1000);
    }
}
