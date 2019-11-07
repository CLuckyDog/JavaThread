package com.rh.chapter_five;

import com.sun.xml.internal.bind.v2.runtime.output.Pcdata;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/6 16:47
 * @description: 消费者类
 * @modified By:
 */
public class Consumer implements Runnable{
    private BlockingQueue<PCData> queue;/*内存缓冲区*/
    private static final int SLEEPTIME=100;

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random r=new Random();

        System.out.println("start Consumer id="+Thread.currentThread().getId());

        try {
            while (true){
                PCData data=queue.take();/*提取任务*/
                if (data!=null){
                    int re=data.getData()*data.getData();
                    System.out.println(
                            MessageFormat.format("{0}*{1}={2}",data.getData(),data.getData(),re)
                    );
                    Thread.sleep(r.nextInt(SLEEPTIME));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
