package com.rh.bilibili.chapter05;   // 解决package

import lombok.extern.slf4j.Slf4j;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: ModifyInterruptMode
 * @Description: 用volatile来代替interrupt实现两阶段终止模式
 * @author: pzj
 * @date: 2021/2/16 17:54
 */
@Slf4j(topic = "c.ModifyInterruptMode")
public class ModifyInterruptMode {

    public static void main(String[] args) throws InterruptedException {
        InterruptMode.start();

        Thread.sleep(4000);

        InterruptMode.stop();
    }
}

@Slf4j(topic = "c.InterruptMode")
class InterruptMode {
    private static Thread monitor;
    static volatile boolean flag=false;

    public static void start() {

        monitor = new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            log.debug("currentThread  Name :" + currentThread.getName());
            while (true) {
                if (flag) {
                    log.debug("线程被打断，处理后续事项！");
                    break;
                }
                try {
                    log.debug("monitor 运行.....");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "monitor");
        monitor.start();

    }

    public static void stop() {
        log.debug("stop 方法被执行！");
        flag = true;
        //这里加上interrupt操作，是为了当线程执行到sleep的时候，flag被修改了，此时，依然要执行完sleep
        // 加上后在sleep时会进入catch，直接进入下一轮循环
        // 判断flag，实现立即终止的效果
        monitor.interrupt();
        log.debug("线程名称：" + monitor.getName());
        log.debug("中断标志：" + monitor.isInterrupted());
    }
}