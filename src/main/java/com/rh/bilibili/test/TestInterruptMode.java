package com.rh.bilibili.test;

import lombok.extern.slf4j.Slf4j;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/1/29
 * \* Time: 9:17
 * \* Description: 两阶段终止模式
 * \
 */
@Slf4j(topic = "c.TestInterruptMode")
public class TestInterruptMode {
    public static void main(String[] args) throws InterruptedException {
        InterruptMode.start();

        Thread.sleep(4000);

        InterruptMode.stop();

    }

    static class InterruptMode {
        private static Thread monitor;

        public static void start() {

            monitor = new Thread(() -> {
                Thread currentThread = Thread.currentThread();
                log.debug("currentThread  Name :" + currentThread.getName());
                while (true) {
                    if (currentThread.isInterrupted()) {
                        log.debug("线程被打断，处理后续事项！");
                        break;
                    }
                    try {
                        log.debug("monitor 运行.....");
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        currentThread.interrupt();
                    }
                }
            }, "monitor");
            monitor.start();

        }

        public static void stop() {
            log.debug("stop 方法被执行！");
            monitor.interrupt();
            log.debug("线程名称：" + monitor.getName());
            log.debug("中断标志：" + monitor.isInterrupted());
        }
    }
}