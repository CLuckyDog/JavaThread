package com.rh.chapter_five.jdkfuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/8 14:59
 * @description:
 * @modified By:
 */
public class FutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*构造FutureTask*/
        FutureTask<String> future=new FutureTask<>(new RealData("xxx"));
        ExecutorService executor= Executors.newFixedThreadPool(1);
        /*执行FutureTask，相当于上例中的clien.request("name")发送请求*/
        /*在这里开启线程进行RealData的call()执行*/
        executor.submit(future);

        System.out.println("请求完毕！");

        try {
            /*这里依然可以做额外的数据操作，这里使用sleep代替其他业务逻辑的处理*/
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*相当于5.5.2节中的data.getResult(),取得call()方法的返回值*/
        /*如果此时call()方法没有执行完成，则依然会等待*/
        System.out.println("数据="+future.get());
    }
}
