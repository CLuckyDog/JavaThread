package com.rh.bilibili.chapter08;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestPool
 * @Description:
 * @author: pzj
 * @date: 2021/3/13 11:25
 */
@Slf4j(topic = "c.TestPool")
public class TestPool {
    public static void main(String[] args) {
        final ThreadPool threadPool = new ThreadPool(1, 1000, TimeUnit.MILLISECONDS,
                1,
                (queue,task)->{
                    //1、死等
//                    queue.put(task);
                    // 2、带超时等待
//                    queue.offer(task,1500,TimeUnit.MILLISECONDS);
                    //3、放弃任务执行
//                    log.debug("放弃当前任务:{}",task);
                    //4、抛出异常
//                    throw new RuntimeException("任务执行失败，超时了！"+task);
                    //5、让调用者自己执行任务
                    task.run();
                }

                );

        for (int i = 0; i <4; i++) {
            int j = i;
            threadPool.execute(()->{
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}",j);
            });
        }
    }
}

@Slf4j(topic = "c.ThreadPool")
class ThreadPool{
    //任务队列
    private BlockingQueue<Runnable> taskQueue;

    //线程集合
    private HashSet<Worker> workers = new HashSet();

    //核心线程数
    private int coreSize;

    //获取任务的超时时间
    private long timeout;

    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    //执行任务
    public void execute(Runnable task){
        //当任务数没有超过coreSize时，直接交给worker对象执行
        //如果任务数超过coreSize时，加入任务队列暂存
        synchronized (workers){
            if (workers.size() < coreSize){
                Worker worker = new Worker(task);
                log.debug("新增 worker {},{}",worker,task);
                workers.add(worker);
                worker.start();
            }else {
//                taskQueue.put(task);
                //1、死等
                // 2、带超时等待
                //3、放弃任务执行
                //4、抛出异常
                //5、让调用者自己执行任务
                taskQueue.tryPut(rejectPolicy,task);
            }
        }

    }

    @FunctionalInterface
    interface RejectPolicy<T>{
        void reject(BlockingQueue<T> queue,T task);
    }

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit,int queueCapacity,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue=new BlockingQueue<>(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            //执行任务
            // 1、 当 task 不为空，执行任务
            // 2、 当task 执行完毕，再接着从任务队列获取任务并执行
//            while (task != null || (task = taskQueue.take()) != null){
            while (task != null || (task = taskQueue.poll(timeout,timeUnit)) != null){
                try {
                    log.debug("正在执行...{}",task);
                    task.run();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    task= null;
                }
            }
            synchronized (workers){
                log.debug("woker 被移除{}",this);
                workers.remove(this);
            }
        }
    }
}

@Slf4j(topic = "c.BlockingQueue")
class BlockingQueue<T>{
    //1、任务队列
    private Deque<T> queue= new ArrayDeque<>();

    //2、锁
    private ReentrantLock lock = new ReentrantLock();

    //3、生产者条件变量
    private Condition fullWatiSet = lock.newCondition();

    //4、消费者条件变量
    private Condition emptyWatiSet = lock.newCondition();

    //5、容量
    private  int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    //超时阻塞获取
    public T poll(long timeout, TimeUnit unit){
        lock.lock();
        try {
             long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()){
                try {
                    if (nanos <=0){
                        return null;
                    }
                    nanos = emptyWatiSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            final T t = queue.removeFirst();
            fullWatiSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    //阻塞获取
    public T take(){
        lock.lock();
        try {
            while (queue.isEmpty()){
                try {
                    emptyWatiSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            final T t = queue.removeFirst();
            fullWatiSet.signal();
            return t;
        }finally {
            lock.unlock();
        }

    }

    //带超时时间的阻塞添加
    public boolean offer(T task , long timeout,TimeUnit timeUnit){
        lock.lock();
        try {
             long nanos = timeUnit.toNanos(timeout);
            while (queue.size() >= capacity){
                try {
                    log.debug("等待加入任务队列{}...",task);
                    if (nanos <= 0){
                        log.debug("超时等待{}...",task);
                        return false;
                    }
                    nanos = fullWatiSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列{}",task);
            queue.addLast(task);
            emptyWatiSet.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }

    //阻塞添加
    public void put(T element){
        lock.lock();
        try {
            while (queue.size() >= capacity){
                try {
                    log.debug("等待加入任务队列{}...",element);
                    fullWatiSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列{}",element);
            queue.addLast(element);
            emptyWatiSet.signal();
        }finally {
            lock.unlock();
        }
    }

    public int size(){
        lock.lock();
        try {
            return queue.size();
        }finally {
            lock.unlock();
        }
    }

    public void tryPut(ThreadPool.RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            //判断队列是否已经满了
            if (queue.size() >= capacity){
                rejectPolicy.reject(this,task);
            }else {//有空闲
                log.debug("加入任务队列{}",task);
                queue.addLast(task);
                emptyWatiSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}