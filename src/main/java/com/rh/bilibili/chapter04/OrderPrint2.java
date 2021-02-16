package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: OrderPrint2
 * @Description: ReentrantLock + Condition(await + signal)实现多线程顺序打印abc
 * @author: pzj
 * @date: 2021/2/16 15:52
 */
@Slf4j(topic = "c.OrderPrint2")
public class OrderPrint2 {
    public static void main(String[] args) {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        final Condition a = awaitSignal.newCondition();
        final Condition b = awaitSignal.newCondition();
        final Condition c = awaitSignal.newCondition();
        Thread t1 = new Thread(() -> {
            awaitSignal.print("a",a,b,false);
        }, "t1");

        Thread t2 = new Thread(() -> {
            awaitSignal.print("b",b,c,false);
        }, "t2");

        Thread t3 = new Thread(() -> {
            awaitSignal.print("c",c,a,true);
        }, "t3");

        t1.start();
        t2.start();
        t3.start();

        sleep(0.5);
        awaitSignal.lock();
        try {
            System.out.println("开始.....");
            a.signal();
        }finally {
            awaitSignal.unlock();
        }

    }
}

class AwaitSignal extends ReentrantLock {
    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    /**
     *  使用ReentrantLock和Condition实现顺序打印
     * @param content   打印内容
     * @param cur   当前休息室
     * @param next  下一个休息室
     */
    public void print(String content, Condition cur,Condition next,boolean flag){
        lock();
        try {
            for (int i = 0; i < loopNumber; i++) {
                try {
                    cur.await();
                    System.out.print(content);
                    if (flag){
                        System.out.println();
                    }
                    next.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            unlock();
        }
    }
}