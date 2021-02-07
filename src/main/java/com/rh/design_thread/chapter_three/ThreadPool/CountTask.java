package com.rh.design_thread.chapter_three.ThreadPool;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/10/11 17:18
 * @description:
 * 分支/合并框架的目的是以递归的方式将可以并行的任务拆分成更小的任务，然后将每个子任务的结果合并起来生成整体结果。
 * 它是ExecutorService接口的一个实现，它把子任务分配给线程池ForkJoinPool中的工作线程
 * @modified By:
 */
public class CountTask extends RecursiveTask<Long> {
    private static final int THRESHOLD=10000;
    private long start;
    private long end;

    public CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum=0;
        boolean canCompute=(end-start)<THRESHOLD;
        /*这里的if判断，就是一个递归逻辑，在else里面，再次创建自己，调用自己的这个compute方法*/
        if (canCompute){
            for (long i=start;i<=end;i++){
                sum+=i;
            }
        }else {
            /*分成100个小任务*/
            long step=(start+end)/100;
            ArrayList<CountTask> subTasks=new ArrayList<CountTask>();
            long pos=start;
            for (int i=0;i<100;i++){
                long lastOne=pos+step;
                if (lastOne>end){
                    lastOne=end;
                }
                CountTask subTask=new CountTask(pos,lastOne);
                pos+=step+1;
                subTasks.add(subTask);
                /*
                * 这里无需担心fork和join的先后执行问题
                * 因为，几个fork就可以有几个join对应
                * 而无论fork是否在join前执行完毕，join都可以拿到fork的计算结果
                * */
                subTask.fork();
            }
            for (CountTask t:subTasks){
                sum+=t.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
//                CountSum(0,200000L); /*顺序执行*/
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        CountTask task=new CountTask(0,200000L);
        ForkJoinTask<Long> result=forkJoinPool.submit(task);

        try {
            /*
            * 如果在执行get方法时，任务没有结束，那么主线程就会在get方法时等待。
            * */
            long res=result.get();
            System.out.println("sum = "+res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("用时："+(endTime-startTime)+"毫秒");


    }

    public static void CountSum(long start,long end){
        long sum=0;
        for (long i=start;i<=end;i++){
            sum+=i;
        }
        System.out.println("sum:"+sum);
    }
}
