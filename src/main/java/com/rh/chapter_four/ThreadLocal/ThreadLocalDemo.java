package com.rh.chapter_four.ThreadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/10/22 15:42
 * @description: ThreadLocal的简单使用
 * @modified By:
 */
public class ThreadLocalDemo {
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static class ParseDate implements Runnable{
        int i=0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                /*
                * 这里会报错：Exception in thread "pool-1-thread-96" java.lang.NumberFormatException: For input string: ""
                * 出现这个问题的原因，是SimpleDateFormat.parse()方法不是线程安全的，
                * 因此，在线程池中共享这个对象必然导致错误。
                * */
                Date t=sdf.parse("2019-10-22 15:50:50");
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
