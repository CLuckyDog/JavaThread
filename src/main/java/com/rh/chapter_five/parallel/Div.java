package com.rh.chapter_five.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/9 15:05
 * @description:
 * @modified By:
 */
public class Div implements Runnable {
    public static BlockingQueue<Msg> bq=new LinkedBlockingQueue<>();

    @Override
    public void run() {
        while (true){
            try {
                Msg msg=bq.take();
                msg.i=msg.i/2;
                System.out.println(msg.orgStr+"="+msg.i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
