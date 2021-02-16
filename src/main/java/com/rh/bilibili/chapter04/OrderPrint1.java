package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: OrderPrint1
 * @Description: ReentrantLock + wait + notify 实现多线程顺序打印abc
 * @author: pzj
 * @date: 2021/2/16 15:40
 */
@Slf4j(topic = "c.OrderPrint1")
public class OrderPrint1 {
    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1, 5);
        Thread t1 = new Thread(() -> {
            wn.print("a",1,2);
        }, "t1");

        Thread t2 = new Thread(() -> {
            wn.print("b",2,3);
        }, "t2");

        Thread t3 = new Thread(() -> {
            wn.print("c",3,1);
        }, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class WaitNotify1 extends ReentrantLock {
    //打印标记
    private int flag;
    //循环次数
    private int loopNumber;

    public WaitNotify1(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    /**
     * 这个方法是所有线程公用的打印方法
     *
     * @param content  打印内容
     * @param flag     判断标记，当前线程能否打印
     * @param nextFlag 下一个打印的线程
     */
    public  void print(String content, int flag, int nextFlag) {
        this.lock();
        try{
            for (int i = 0; i < loopNumber; i++) {
                while (this.flag != flag) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(content);
                if (nextFlag == 1){
                    System.out.println();
                }
                this.flag = nextFlag;
                notifyAll();
            }
        }finally {
            this.unlock();
        }

    }
}