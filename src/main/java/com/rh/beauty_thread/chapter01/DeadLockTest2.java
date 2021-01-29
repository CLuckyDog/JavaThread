package com.rh.beauty_thread.chapter01;

import lombok.extern.slf4j.Slf4j;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/1/29
 * \* Time: 10:24
 * \* Description:
 * \
 */
@Slf4j(topic = "c.DeadLockTest2")
public class DeadLockTest2 {
    //创建资源
    private static Object resourceA= new Object();
    private static Object resourceB= new Object();

    public static void main(String[] args) {
        //创建线程A
        Thread threadA = new Thread(()->{
            synchronized (resourceA){
                log.debug(Thread.currentThread()+"get resourceA!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug(Thread.currentThread()+"waiting get resourceB!");
                synchronized (resourceB){
                    log.debug(Thread.currentThread()+"get resourceB!");
                }
            }
            //这块资源的申请，必须在上个sync块内，才算构成持有资源并申请其他资源的条件
//            log.debug(Thread.currentThread()+"waiting get resourceB!");
//            synchronized (resourceB){
//                log.debug(Thread.currentThread()+"get resourceB!");
//            }
        },"threadA");

        //创建线程B
        Thread threadB = new Thread(()->{
            synchronized (resourceB){
                log.debug(Thread.currentThread()+"get resourceB!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug(Thread.currentThread()+"waiting get resourceA!");
                synchronized (resourceA){
                    log.debug(Thread.currentThread()+"get resourceA!");
                }
            }
//            log.debug(Thread.currentThread()+"waiting get resourceA!");
//            synchronized (resourceA){
//                log.debug(Thread.currentThread()+"get resourceA!");
//            }
        },"threadB");

        threadA.start();
        threadB.start();

    }

}