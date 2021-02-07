package com.rh.design_thread.chapter_five.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/9 15:00
 * @description:
 * @modified By:
 */
public class Plus implements Runnable {
    public static BlockingQueue<Msg> bq=new LinkedBlockingQueue<>();

    @Override
    public void run() {
        while (true){
            try {
                Msg msg=bq.take();/*如果bq队列里面没有元素，那么take方法会一直await*/
                msg.j=msg.i+msg.j;
                Multiply.bq.add(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
