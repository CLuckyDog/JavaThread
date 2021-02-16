package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: OrderPrint3
 * @Description: 用LockSupport实现多线程顺序打印
 * @author: pzj
 * @date: 2021/2/16 16:17
 */
@Slf4j(topic = "c.OrderPrint3")
public class OrderPrint3 {
    static Thread t1;
    static Thread t2;
    static Thread t3;
    public static void main(String[] args) {
        ParkUnpark pn = new ParkUnpark(5);

         t1 = new Thread(() -> {
            pn.print("a",t2,false);
        }, "t1");

         t2 = new Thread(() -> {
             pn.print("b",t3,false);
        }, "t2");

         t3 = new Thread(() -> {
             pn.print("c",t1,true);
        }, "t3");
        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);//这里不需要等待，因为LockSupport的unpark和park没有固定的顺序关系

    }

}

class ParkUnpark{
    private int loopNumber;

    public ParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    /**
     *  用LockSupport实现顺序打印，因为LockSupport是和线程关联的，所以，要用Thread作为唤醒参数
     * @param content   打印内容
     * @param next  下一个要唤醒的线程
     * @param flag  换行标记
     */
    public void print(String content,Thread next,boolean flag){
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(content);
            if (flag){
                System.out.println();
            }
            LockSupport.unpark(next);
        }
    }
}