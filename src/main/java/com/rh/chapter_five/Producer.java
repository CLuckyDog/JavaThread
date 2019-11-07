package com.rh.chapter_five;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/6 16:46
 * @description: 生产者类
 * @modified By:
 */
public class Producer implements Runnable {
    private volatile boolean isRunning = true;
    private BlockingQueue<PCData> queue;/*内存缓冲区*/
    private static AtomicInteger count=new AtomicInteger();/*总数，原子操作*/
    private static final int SLEEPTIME=100;

    public Producer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        PCData data=null;
        Random r=new Random();

        System.out.println("start producer id="+Thread.currentThread().getId());

        try {
            while (isRunning){
                Thread.sleep(r.nextInt(SLEEPTIME));
                data=new PCData(count.incrementAndGet());/*构造任务数据*/
                System.out.println(data+"is put into queue");
                if (!queue.offer(data,2, TimeUnit.SECONDS)){/*提交任务到缓存区*/
                    System.out.println("failed to put data:"+data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stop(){
        isRunning=false;
    }
}
