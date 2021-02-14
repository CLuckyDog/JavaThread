package com.rh.bilibili.chapter04;

import com.rh.bilibili.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestMultiLock")
public class TestMultiLock {
    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(()->{
            bigRoom.sleep();
        },"小女").start();
        new Thread(()->{
            bigRoom.study();
        },"小南").start();
    }
}

@Slf4j(topic = "c.BigRoom")
class BigRoom{
    //把sync(this)改成下面两个锁对象，细分对BigRoom的锁粒度
    private Object studyRoomLock = new Object();
    private Object sleepRoomLock = new Object();

    public void sleep(){
//        synchronized (this){
        synchronized (sleepRoomLock){
            log.debug("sleeping 2 小时！");
            Sleeper.sleep(2);
        }
    }

    public void study(){
//        synchronized (this){
        synchronized (studyRoomLock){
            log.debug("study 1 小时！");
            Sleeper.sleep(1);
        }
    }
}
