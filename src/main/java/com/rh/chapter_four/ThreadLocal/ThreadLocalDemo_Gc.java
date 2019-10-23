package com.rh.chapter_four.ThreadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/10/23 15:39
 * @description: ThreadLocal的回收示例代码
 * @modified By:
 */
public class ThreadLocalDemo_Gc {
    static volatile ThreadLocal<SimpleDateFormat> tl=new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString()+"ThreadLocal is gc !");
        }
    };
    /*计数数量为10000，表示需要有10000个线程完成任务，等待在CountDownLatch上的线程才能继续执行*/
    static volatile CountDownLatch cd=new CountDownLatch(10000);
    public static class ParseDate implements Runnable{
        int i=0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                /*
                * 这个判断，导致很大可能性，只会看到10个 create SimpleDateFormat! 输出。
                * 但是，也有可能是大于10个，或者小于10个。
                * 因为，第二波任务提交时候，tl.get()方法里面就不为null了。
                * */
                if (tl.get()==null){
                    tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){
                        @Override
                        protected void finalize() throws Throwable {
                            /*
                            * 这行代码的输出，可能很难看到，因为gc回收，是随机的
                            * 不是System.gc()方法调用后，就立即执行回收
                            * */
                            System.out.println(this.toString()+"SimpleDateFormat is gc !");
                        }
                    });
                    System.out.println(Thread.currentThread().getId()+":create SimpleDateFormat!");
                    Date t=tl.get().parse("2019-10-22 15:50:"+i%60);
                }
//                else{
//                    System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
//                }
            } catch (ParseException e) {
                e.printStackTrace();
            }finally {
                /*通知有一个线程完成了任务，倒计时器减去1*/
                cd.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es= Executors.newFixedThreadPool(10);
        for (int i=0;i<10000;i++){
            es.execute(new ParseDate(i));
        }
        /*等待检查，只有当10000个任务执行完毕，才执行await后面的代码*/
        cd.await();
        System.out.println("mission complete!");
        tl=null;
        /*调用系统GC，进行垃圾清理！*/
        System.gc();
        System.out.println("first GC complete!!");
        /*
        * 在设置ThreadLocal的时候，会清除ThreadLocalMap中的无效对象
        * */
        tl=new ThreadLocal<>();
        cd=new CountDownLatch(10000);
        for (int i=0;i<10000;i++){
            es.execute(new ParseDate(i));
        }
        cd.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("second GC complete!!");

    }
}
