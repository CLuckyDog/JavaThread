package com.rh.design_thread.chapter_four.ThreadLocal;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2019/10/26.
 * 用ThreadLocal生成随机数，比较性能实例代码
 * 结果表明ThreadLocal独享变量，比多线程共享变量性能高
 *
 */
public class TestRandomByThread {
    /*定义每个线程要产生随机数的数量*/
    public static final int GEN_COUNT=10000000;
    /*定义线程数量*/
    public static final int THREAD_COUNT=4;
    /*创建含有4个活跃线程的线程池*/
    static ExecutorService exe= Executors.newFixedThreadPool(THREAD_COUNT);
    public static Random rnd=new Random(123);

    /*创建由ThreadLocal封装的Random*/
    /*这样包装之后，每个线程都有自己的Random，从而提升了性能*/
    public static ThreadLocal<Random> tRnd=new ThreadLocal<Random>(){
        @Override
        protected Random initialValue() {
            return new Random(123);
        }
    };

    public static class RndTask implements Callable<Long>{
        private int mode =0;

        public RndTask(int mode) {
            this.mode = mode;
        }

        public Random getRandom(){
            if (mode==0){
                return rnd;
            }else if (mode == 1){
                return tRnd.get();
            }else{
                return null;
            }
        }

        @Override
        public Long call() throws Exception {

            long b=System.currentTimeMillis();
            for (long i=0;i<GEN_COUNT;i++){
                getRandom().nextInt();
            }
            long e=System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+"spend"+(e-b)+"ms");

            return e-b;
        }
    }
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        
        Future<Long>[] futs=new Future[THREAD_COUNT];
        for (int i=0;i<THREAD_COUNT;i++){
            futs[i]=exe.submit(new RndTask(0));
        }

        long totalTime=0;
        for (int i=0;i<THREAD_COUNT;i++){
            totalTime+=futs[i].get();
        }
        
        System.out.println("多线程访问同一个Random实例："+totalTime+"ms");

        /*ThreadLocal的情况*/
        for (int i=0;i<THREAD_COUNT;i++){
            futs[i]=exe.submit(new RndTask(1));
        }

        totalTime=0;
        for (int i=0;i<THREAD_COUNT;i++){
            totalTime+=futs[i].get();
        }

        System.out.println("使用ThreadLocal包装Random实例："+totalTime+"ms");
        exe.shutdown();
    }

}
