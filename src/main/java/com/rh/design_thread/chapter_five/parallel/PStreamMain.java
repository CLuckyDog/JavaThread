package com.rh.design_thread.chapter_five.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/9 15:09
 * @description:
 * @modified By:
 */
public class PStreamMain {
    public static void main(String[] args) throws InterruptedException {
//        new Thread(new Plus()).start();
//        new Thread(new Multiply()).start();
//        new Thread(new Div()).start();

        ExecutorService es= Executors.newFixedThreadPool(3);

        es.submit(new Plus());
        es.submit(new Multiply());
        es.submit(new Div());

        Thread.sleep(3000);

        for(int i=1;i<=1000;i++){
            for (int j=1;j<=1000;j++){
                Msg msg=new Msg();
                msg.i=i;
                msg.j=j;
                msg.orgStr="(("+i+"+"+j+")*"+i+")/2";
                Plus.bq.add(msg);
            }
        }

        es.shutdown();

    }
}
