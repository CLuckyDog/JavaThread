package com.rh.bilibili.chapter04;

import lombok.extern.slf4j.Slf4j;

import static com.rh.bilibili.utils.Sleeper.sleep;

@Slf4j(topic = "c.TestCorrectPostureStep1")
public class TestCorrectPostureStep1 {
    static final Object room =new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (room){
                log.debug("有烟吗？[{}]",hasCigarette);
                if (!hasCigarette){
                    log.debug("没烟，先歇会！");
//                    sleep(2);//sleep方法，不会让线程释放锁
                    //优化1:把sleep方式改成wait方式,这样，当线程调用wait的时候，会释放锁，让其他线程使用资源
                    //          wait线程可以用notify或者notifyAll方法唤醒，也可以用异常interrupt方式唤醒
                    //          wait和notify都需要在获取锁之后，才能调用，否则编译不通过
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟吗？[{}]",hasCigarette);
                if (hasCigarette){
                    log.debug("可以开始干活了！");
                }else {
                    log.debug("没干成活...");
                }
            }
        },"小南").start();

        for (int i =0;i<5;i++){
            new Thread(()->{
                synchronized (room){
                    log.debug("可以开始干活了！");
                }
            },"其他人"+i).start();
        }

        sleep(1);
        new Thread(() -> {
            // 这里能不能加 synchronized (room)？
            synchronized (room) {
                hasCigarette = true;
                log.debug("烟送到了！");
                room.notify();
            }
        }, "送烟的").start();

    }

}
