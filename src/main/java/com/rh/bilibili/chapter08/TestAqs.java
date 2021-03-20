package com.rh.bilibili.chapter08;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestAqs
 * @Description:
 * @author: pzj
 * @date: 2021/3/20 11:41
 */
@Slf4j(topic = "c.TestAqs")
public class TestAqs {
    public static void main(String[] args) {

//        MyLock lock = new MyLock();
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            lock.lock();
            log.debug("locking 1 ......");
            lock.lock();
            log.debug("locking 2 ......");
            try {
                log.debug("locking......");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                lock.unlock();
                log.debug("unlocking......");
            }
        }, "t1");
        t1.start();

//        Thread t2 = new Thread(() -> {
//            lock.lock();
//            try {
//                log.debug("locking......");
//            }finally {
//                lock.unlock();
//                log.debug("unlocking......");
//            }
//        }, "t2");
//        t2.start();
    }
}

class MyLock implements Lock {

    class MySync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0,1)){
                //加锁成功，并设置owner为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    MySync sync = new MySync();

    @Override//加锁，不成功则进入等待队列
    public void lock() {
        sync.acquire(1);
    }

    @Override//加锁，可打断
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override//尝试加锁一次
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override//尝试加锁带超时
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override//解锁
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}