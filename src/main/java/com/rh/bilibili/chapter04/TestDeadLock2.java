package com.rh.bilibili.chapter04;

import com.rh.bilibili.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 哲学家就餐问题
 */
@Slf4j(topic = "c.TestDeadLock2")
public class TestDeadLock2 {
    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");
        new Philosopher("苏格拉底", c1, c2).start();
        new Philosopher("柏拉图", c2, c3).start();
        new Philosopher("亚里士多德", c3, c4).start();
        new Philosopher("赫拉克利特", c4, c5).start();
        new Philosopher("阿基米德", c5, c1).start();  //  造成死锁
//        new Philosopher("阿基米德", c1, c5).start();  //造成饥饿
    }
}

@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread {
    Chopstick left;
    Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
//            //　尝试获得左手筷子
//            synchronized (left) {
//                // 尝试获得右手筷子
//                synchronized (right) {
//                    eat();
//                }
//            }
            //使用ReentrantLock优化，解决死锁和饥饿问题
            //　尝试获得左手筷子
            if (left.tryLock()){
                try {
                    //　尝试获得右手筷子
                    if (right.tryLock()){
                        try {
                            eat();
                        }finally {
                            right.unlock();
                        }
                    }
                }finally {
                    left.unlock();//关键在这个地方，当获取到左筷子后，没有获取到右筷子时，直接释放左筷子锁
                }
            }
        }
    }

    private void eat() {
        log.debug("eating...");
        Sleeper.sleep(1);
    }
}

class Chopstick extends ReentrantLock {
    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}
