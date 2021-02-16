package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: OrderPrint
 * @Description: sync+wait+notify 实现多线程顺序打印abc
 * @author: pzj
 * @date: 2021/2/16 15:26
 */
@Slf4j(topic = "c.OrderPrint")
public class OrderPrint {
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

/**
 * 打印内容     打印标记      下一个
 * a                 1                  2
 * b               2                   3
 * c               3                   1
 */
class WaitNotify {
    //打印标记
    private int flag;
    //循环次数
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
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
    public synchronized void print(String content, int flag, int nextFlag) {
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
    }
}