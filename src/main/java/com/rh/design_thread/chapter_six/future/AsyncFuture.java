package com.rh.design_thread.chapter_six.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/18 10:05
 * @description: 异步CompletableFuture 示例
 * @modified By:
 */
public class AsyncFuture {

    public static Integer calc(Integer para){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para*para;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
        * ()->calc(60)  lambda表达式
        * 空括号表示无参数  -> 指向执行体  执行体里面执行 calc(60)方法
        * */
//        final CompletableFuture<Integer> future=CompletableFuture.supplyAsync(()->calc(60));
        /*流式调用*/
//        CompletableFuture<Void> future=CompletableFuture.supplyAsync(()->calc(60))
//                .thenApply(i->Integer.toString(i)).thenApply((str)->"\""+str+"\"")
//                .thenAccept(System.out::println);
        /*异常处理*/
        CompletableFuture<Void> future=CompletableFuture.supplyAsync(()->calc(60))
                .exceptionally(ex->{
                    System.out.println(ex.toString());
                    return 0;
                })
                .thenApply(i->Integer.toString(i)).thenApply((str)->"\""+str+"\"")
                .thenAccept(System.out::println);
        /*
        * future.get() 如果异步执行还没有计算出结果，那么这个get方法会进行等待。
        * */
        System.out.println("future result:"+future.get());
    }
}
