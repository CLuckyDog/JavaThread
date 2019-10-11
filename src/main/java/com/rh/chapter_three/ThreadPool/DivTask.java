package com.rh.chapter_three.ThreadPool;

import java.util.concurrent.*;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/10/11 14:36
 * @description:    求两个数的商值
 * @modified By:
 */
public class DivTask implements Runnable{
    int a,b;

    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        double re=a/b;
        System.out.println("Thread "+Thread.currentThread().getId()+"计算结果是："+re);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ThreadPoolExecutor pools=new ThreadPoolExecutor(0,Integer.MAX_VALUE,0L,
//                TimeUnit.MILLISECONDS,new SynchronousQueue<Runnable>());
        ThreadPoolExecutor pools=new TraceThreadPoolExecutor(0,Integer.MAX_VALUE,0L,
                TimeUnit.MILLISECONDS,new SynchronousQueue<Runnable>());

        for (int i=0;i<5;i++){
            /*无堆栈信息提示*/
//            pools.submit(new DivTask(100,i));
            /*有部分堆栈信息,不终止程序*/
            pools.execute(new DivTask(100,i+1));
            /*有部分堆栈信息,并终止程序*/
//            Future re=pools.submit(new DivTask(100,i));
//            re.get();
        }

    }
}
