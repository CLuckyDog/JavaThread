package com.rh.beauty_thread.chapter06;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: NonReentrantLock
 * @Description:
 * @author:pzj
 * @date: 2021/2/19 14:24
 */
@Slf4j(topic = "c.NonReentrantLock")
public class NonReentrantLock implements Lock, Serializable{

    private static final long serialVersionUID = -1092532426645979010L;

    /**
     * 内部帮助类
     */
    private static class Sync extends AbstractQueuedSynchronizer{
        /**
         * 如果state为0，则尝试获取锁
         * @param acquires
         * @return
         */
        @Override
        protected boolean tryAcquire(int acquires) {
            //断言
            assert acquires == 1;
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 尝试释放锁，设置state为0
         * @param release
         * @return
         */
        @Override
        protected boolean tryRelease(int release) {
            assert release==1;
            if (getState() == 0){
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 是否已经持有锁
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        /**
         * 提供条件变量接口
         * @return
         */
        Condition newCondition(){
            return new ConditionObject();
        }
    }

    //创建一个Sync来做具体的工作
    private final Sync sync= new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(timeout));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked(){
        return sync.isHeldExclusively();
    }

    public static void main(String[] args) {
        new Thread(() -> {
        }, "t1").start();
    }
}
