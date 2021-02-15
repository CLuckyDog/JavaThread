package com.rh.bilibili.chapter04;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: ReentrantLockCondition
 * @Description: 用ReentrantLock里的Condition来优化送烟送外卖的案例
 * @author: pzj
 * @date: 2021/2/15 16:02
 */
@Slf4j(topic = "c.ReentrantLockCondition")
public class ReentrantLockCondition {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    //定义一个ReentrantLock锁
    static ReentrantLock roomLock= new ReentrantLock();
    //定义一个等烟的休息室
    static Condition waitCigaretteSet= roomLock.newCondition();
    //定义一个等外卖的休息室
    static Condition waitTakeoutSet= roomLock.newCondition();

    public static void main(String[] args) {


        new Thread(() -> {
            roomLock.lock();
            try {
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) {//while防止虚假唤醒
                    log.debug("没烟，先歇会！");
                    try {
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("烟到了，可以开始干活了");
            }finally {
                roomLock.unlock();
            }
        }, "小南").start();

        new Thread(() -> {
            roomLock.lock();
            try {
                log.debug("外卖送到没？[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖，先歇会！");
                    try {
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖到了，可以开始干活了");
            }finally {
                roomLock.unlock();
            }
        }, "小女").start();

        sleep(1);
        new Thread(() -> {
            roomLock.lock();
            try {
                hasTakeout = true;
                log.debug("外卖到了噢！");
                waitTakeoutSet.signal();
            }finally {
                roomLock.unlock();
            }
        }, "送外卖的").start();

        sleep(1);
        new Thread(() -> {
            roomLock.lock();
            try {
                hasCigarette = true;
                log.debug("烟到了噢！");
                waitCigaretteSet.signal();
            }finally {
                roomLock.unlock();
            }
        }, "送烟的").start();


    }

}