package com.rh.beauty_thread.chapter07;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/3/9
 * \* Time: 16:13
 * \* Description:
 * \
 */
public class TestDelay {

    public static void main(String[] args) {
        //1、创建delay队列
        DelayQueue<DelayEle> delayQueue= new DelayQueue<>();

        //2、创建延时任务
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            DelayEle delayEle = new DelayEle(random.nextInt(500), "task:" + i);
            delayQueue.offer(delayEle); // 添加元素到队列里的时候，就进行了堆排序，排序规则是我们自己定义的compareTo规则
        }

        //3、依次取出任务并打印
        DelayEle ele= null;
        try {
            //3.1、循环，如果想避免虚假唤醒，则不能把全部元素都打印出来
            for (;;){
                //3.2、获取国企任务并打印
                while ((ele = delayQueue.take()) != null){  //获取的时候，从堆顶取出元素
                    System.out.println(ele.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class DelayEle implements Delayed{//Delayed累已经继承了Comparable接口

        private final long delayTime;   //延迟时间
        private final long expire;      //到期时间
        private String taskName;    //任务名称

        public DelayEle(long delayTime,  String taskName) {
            this.delayTime = delayTime;
            this.expire = System.currentTimeMillis()+delayTime;
            this.taskName = taskName;
        }

        /**
         * 剩余时间=到期时间-当前时间
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }

        /**
         * 优先级队列里面的优先级规则
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS)-o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "DelayEle{" +
                    "delayTime=" + delayTime +
                    ", expire=" + expire +
                    ", taskName='" + taskName + '\'' +
                    '}';
        }
    }
}