package com.rh.beauty_thread.chapter01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/1/21
 * \* Time: 11:05
 * \* Description:
 * \
 */
public class ThreadTest {

    //1、继承Thread类并重写run方法
    public static class MyThread extends Thread{

        private String name;
        private Integer age;

        public MyThread(String name,Integer age){
            this.name=name;
            this.age=age;
        }

        @Override
        public void run() {
            System.out.println("I am a child thread,My name is "+name+",I am "+age+" years old!");
        }
    }

    //2、实现Runable接口的run方法
    public static class RunableTask implements Runnable{

        private String name;
        private Integer age;

        public RunableTask(String name,Integer age){
            this.name=name;
            this.age=age;
        }

        @Override
        public void run() {
            System.out.println("I am a child thread,My name is "+name+",I am "+age+" years old!");
        }
    }

    //3、创建任务类，类似Runable
    public static class CallerTask implements Callable<String>{
        @Override
        public String call() throws Exception {
            return "hello1";
        }
    }


    public static void main(String[] args) {
        //1
        MyThread thread=new MyThread("TomCat",20);
        thread.start();

        //2
        RunableTask task= new RunableTask("TomCat1",21);
        new Thread(task).start();
        new Thread(task).start();

        final String name="tomcat";
        final Integer age=21;

        new Thread(()->{
            System.out.println(System.currentTimeMillis());
            System.out.println("I am a child thread,My name is "+name+",I am "+age+" years old!");
        }).start();

        //3
        //创建异步任务
        FutureTask<String> futureTask = new FutureTask<>(new CallerTask());
        //启动线程
        new Thread(futureTask).start();
        try {
            //等待任务执行完毕，并返回结构
            //在这个get方法之前，所有的任务都是并发的，类似MapReduce思想。
            String result = futureTask.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}