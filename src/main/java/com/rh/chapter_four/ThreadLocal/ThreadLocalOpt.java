package com.rh.chapter_four.ThreadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/10/22 16:52
 * @description:
 * @modified By:
 */
public class ThreadLocalOpt {
    static ThreadLocal<SimpleDateFormat> tl=new ThreadLocal<>();
    public static class ParseDate implements Runnable{
        int i=0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                /*
                * 如果当前线程不持有SimpleDateFormat对象实例
                * 那就新建一个并把它设置到当前线程中，如果有，则直接使用
                * */
                if (tl.get()==null){
                    tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t=tl.get().parse("2019-10-22 15:50:"+i%60);
                System.out.println(i+":"+t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService es= Executors.newFixedThreadPool(10);
        for(int i=0;i<1000;i++){
            es.execute(new ParseDate(i));
        }
    }

}
