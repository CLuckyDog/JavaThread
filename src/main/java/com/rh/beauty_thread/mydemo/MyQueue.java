package com.rh.beauty_thread.mydemo;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: MyQueue
 * @Description:
 * @author:pzj
 * @date: 2021/4/2 9:49
 */
@Slf4j(topic = "c.MyQueue")
public class MyQueue {

    private static Map<String, Object> msgMap = new HashMap<>();

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition notEmpty = lock.newCondition();


    public static boolean addMsg(String keyStr, Object vObj) {
        lock.lock();
        boolean result = false;
        try {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            msgMap.put(keyStr, vObj);
            result = msgMap.containsValue(vObj);

            if (result) {
//                System.out.println(Thread.currentThread().getName() + "    通知.....");
                log.debug("通知.....");
                notEmpty.signalAll();
            }

        } finally {
//            System.out.println(Thread.currentThread().getName() + "    释放lock。。。");
            log.debug("释放lock。。。");
            lock.unlock();
        }
        return result;
    }

    public static void takeMsg() {
        lock.lock();
        try {
            while (msgMap.isEmpty()) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Iterator iter = msgMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Student val = (Student) msgMap.get(key);
//                System.out.println(Thread.currentThread().getName() + "    key:" + key + "    val:" + val.toString());
                log.debug("key:"+key+"    val:"+val.toString());
                iter.remove();
            }

        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(10, new MyThreadFactory("MyQueuePool"));
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                while (true) {
                    int a = random.nextInt(100000);
                    int c = random.nextInt(100000);
                    int d = random.nextInt(100000);
                    boolean b = MyQueue.addMsg("keyStr" + a, new Student("stu" + c, d));
//                    System.out.println(Thread.currentThread().getName() + "     添加学生" + c + "的信息：" + b);
                log.debug("添加学生"+c+"的信息："+b);
                }
            });
        }

//        new Thread(()->{
//            Random random = new Random();
//
//            while (true){
//                int i = random.nextInt(100000);
//                int j = random.nextInt(100000);
//                int k = random.nextInt(100000);
//                boolean b = MyQueue.addMsg("keyStr" + i, new Student("stu" + j, k));
//                System.out.println(Thread.currentThread().getName()+"     添加学生"+"stu" + j+"的信息："+b);
//            }
//            },"MyTakeThreadProducter").start();

        new Thread(() -> {
            while (true)
                MyQueue.takeMsg();
        }, "MyTakeThreadConsumer").start();

    }
}
