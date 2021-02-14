package com.rh.bilibili.chapter04;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * 保护性暂停模式
 */
@Slf4j(topic = "c.DesignPatterns1")
public class DesignPatterns1 {

//    public static void main(String[] args) {
//    GuardedObject guardedObject = new GuardedObject(1);
//    // 线程1 等待 线程2 下载的结果
//    new Thread(()->{
//        //等待结果
//        log.debug("等待结果...");
//        List<String> list = (List<String>) guardedObject.get(2000);
//        log.debug("结果大小：{}",list==null?null:list.size());
//    },"t1").start();
//
////        new Thread(()->{
////            log.debug("执行下载...");
////            try {
////                List<String> list = Downloader.download();
////                guardedObject.complete(list);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        },"t2").start();
//
//    new Thread(()->{
//        log.debug("执行下载...");
//        try {
//            sleep(1);
//            List<String> list = new ArrayList<>();
//            list.add("11");
//            guardedObject.complete(list);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    },"t2").start();
//}

    public static void main(String[] args) {
        for (int i =0 ; i < 3; i++){
            new People("People"+(i+1)).start();
        }

        sleep(1);

        for (Integer id : Mailboxes.getIds()) {
            new Postman("Postman"+id,id,"内容"+id).start();
        }
    }
}

@Slf4j(topic = "c.People")
class People extends Thread{

    public People(String name) {
        super(name);
    }

    @Override
    public void run() {
        GuardedObject guardedObject=Mailboxes.createGuardedObject();
        log.debug("开始收信 ID：{}",guardedObject.getId());
        final Object mail = guardedObject.get(5000);
        log.debug("结束收信 ID：{},内容：{}",guardedObject.getId(),mail);
    }
}

@Slf4j(topic = "c.Postman")
class Postman extends Thread{
    private int id;
    private String mail;

    public Postman(String name,int id, String mail) {
        super(name);
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        final GuardedObject guardedObject = Mailboxes.getGuardedObject(id);
        log.debug("开始送信 ID：{}，内容：{}",id,mail);
        guardedObject.complete(mail);
    }
}

class Mailboxes{
    //Hashtable是线程安全的
    private static Map<Integer,GuardedObject> boxes= new Hashtable<>();

    private static int id=1;

    //产生唯一ID
    private static synchronized int generateId(){
        return id++;
    }

    public static GuardedObject createGuardedObject(){
        GuardedObject go=new GuardedObject(generateId());
        boxes.put(go.getId(),go);
        return go;
    }

    public static GuardedObject getGuardedObject(int id){
        return boxes.remove(id);
    }

    public static Set<Integer> getIds(){
        return boxes.keySet();
    }
}

/**
 * 第一次优化：增加超时限制
 */
class GuardedObject{

    //标识
    private int id;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //结果
    private Object response;

    //获取结果
    //timeout 表示等待多久
    public Object get(long timeout){
        //开始时间
        long begin=System.currentTimeMillis();
        //经历的时间
        long passedTime = 0;
        synchronized (this){
            while (response == null){
                //这里减去passedTime的作用是，防止虚假唤醒，如果在等待timeout时间，就超过等待时间了
                long waitTime=timeout - passedTime;
                //经历的时间超过最大等待时间，退出循环
                if (waitTime<=0){
                    break;
                }
                try {

                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //求得经历的时间
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }

    //产生结果
    public void complete(Object response){
        synchronized (this){
            //给结果成员变量赋值
            this.response=response;
            this.notifyAll();
        }
    }
}
