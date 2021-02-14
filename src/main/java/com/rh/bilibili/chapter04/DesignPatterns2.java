package com.rh.bilibili.chapter04;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * 消费者生产者模式
 */
@Slf4j(topic = "c.DesignPatterns2")
public class DesignPatterns2 {

    public static void main(String[] args) {

        MessageQueue queue= new MessageQueue(2);

        for (int i = 0 ;i<3;i++){
            final int id = i;
            new Thread(()->{
                queue.put(new Message(id,"消息内容："+id));
            },"生产者"+i).start();
        }

        new Thread(()->{
            //不停止的循环取消息
            while (true){
                //间隔1秒取出消息
                sleep(1);
                final Message message = queue.take();
//                log.debug("取出消息：{}",message);
            }
        },"消费者").start();
    }

}

//消息队列，Java线程级
@Slf4j(topic = "c.MessageQueue")
class MessageQueue{
    //双向链表作为存放消息的容器
    private LinkedList<Message> list = new LinkedList();
    //队列容量
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    //获取消息的方法
    public Message take(){
        //检测队列是否为空
        synchronized (list){
            //如果队列为空，则进入等待
            while (list.isEmpty()){
                try {
                    log.debug("队列已空，消费者线程请等待！");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //从队列的头部获取消息返回
            final Message message = list.removeFirst();
            log.debug("已消费消息：{}",message);
            //告诉生产者线程，有消息取出，可以再生产了
            list.notifyAll();
            return message;
        }
    }

    //存入消息的方法
    public void put(Message message){
        synchronized (list){
            //检测队列是否已满
            while (list.size() == capcity){
                try {
                    log.debug("队列已满，生产者线程请等待！");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //把新消息存放到链表尾部
            list.addLast(message);
            log.debug("已生产消息：{}",message);
            //告诉消费者线程，有消息存入，可以消费了
            list.notifyAll();
        }
    }
}

final class Message{
    private int id;
    private Object content;

    public Message(int id, Object content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public Object getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content=" + content +
                '}';
    }
}
