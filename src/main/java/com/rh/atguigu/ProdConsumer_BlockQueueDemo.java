package com.rh.atguigu;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列的案例代码
 */
public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t  生产线程启动。。。");
            try {
                myResource.myProd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t  消费线程启动。。。");
            try {
                myResource.myConsumer();
                System.out.println();
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("5秒钟时间到，大老板main线程叫停，活动结束！");
        myResource.stop();
    }
}

class MyResource {
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue;

    //参数设置成阻塞队列的接口类型，符合面向接口开发的设计思路
    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println("阻塞队列名称：" + blockingQueue.getClass().getName());
    }

    public void myProd() throws InterruptedException {
        String data;
        boolean retValue;
        while (FLAG) {
            data = String.valueOf(atomicInteger.incrementAndGet());
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t  插入队列" + data + "成功！");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t  插入队列" + data + "失败！");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t  大老板叫停了，表示FLAG=false，生产动作结束！");
    }

    public void myConsumer() throws InterruptedException {
        String data;
        while (FLAG) {
            data = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (data== null || data.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t  超过2秒钟没有取到蛋糕，消费结束！");
                System.out.println();
                System.out.println();
                break; //结束循环
            }
            System.out.println(Thread.currentThread().getName() + "\t  消费队列蛋糕" + data + "成功！");
        }
    }

    public void stop() {
        this.FLAG = false;
    }
}