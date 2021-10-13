package com.rh.atguigu;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: pzj
 * \* Date: 2021/10/13
 * \* Time: 21:07
 * \* Description:
 * \
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable callable = new MyCallable();
        //这个task实例，只能被一个线程使用1次，多个线程使用的话，只会执行一次
        //想要任务被执行两次，需要再new一个task实例，传给另外一个线程
        FutureTask<Integer> task = new FutureTask<>(callable);
        Thread thread = new Thread(task, "callable-test");
        thread.start();

        Integer result01 = 100;
        /*
            FutureTask主要永在Fork-Join框架中
            线程没有结束之前，这个get是获取不到计算结果的，main线程会阻塞在这里，等待结果
            所以，建议这个获取结果的操作尽量放在最后
            while (!task.isDone()){}
         */

        Integer result02 = task.get();
        System.out.println("最终结果："+(result01 + result02));
    }
}

class MyCallable implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        return 1111;
    }
}