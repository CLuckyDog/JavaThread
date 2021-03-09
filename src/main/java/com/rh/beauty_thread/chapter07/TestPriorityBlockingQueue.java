package com.rh.beauty_thread.chapter07;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/3/9
 * \* Time: 15:56
 * \* Description:
 * \
 */
public class TestPriorityBlockingQueue {

    public static void main(String[] args) {
        //创建任务，并添加到队列
        PriorityBlockingQueue<Task> priorityQueue = new PriorityBlockingQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setPriority(random.nextInt(10));
            task.setTaskName("taskName"+i);
            priorityQueue.offer(task);//此处，offer进队列的元素，已经进行了堆排序
        }

        //取出任务并执行
        while (!priorityQueue.isEmpty()){
            Task poll = priorityQueue.poll();//此处取出的是堆顶元素，就是队列的第一个元素，优先级最小的那个task
            if (poll != null){
                poll.doSomeThing();
            }
        }
    }

    static class Task implements Comparable<Task>{
        private int priority = 0;
        private String taskName;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public int compareTo(Task o) {
            if (this.priority >= o.getPriority()){
                return 1;
            }else{
                return -1;
            }
        }

        public void doSomeThing(){
            System.out.println(taskName+":"+priority);
        }
    }
}