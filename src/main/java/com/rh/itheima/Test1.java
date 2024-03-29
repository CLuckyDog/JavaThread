package com.rh.itheima;

import com.rh.design_thread.chapter_three.ReenterLock;
import com.rh.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: pzj
 * \* Date: 2021/9/30
 * \* Time: 16:05
 * \* Description:
 * \
 */
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        User user = new User();
        user.setAccount("aaa");
        changeVal1(user);
        System.out.println(user.getAccount());

        changeVal2(user);
        System.out.println(user.getAccount());
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        condition.await();
        condition.signalAll();
    }

    public static void changeVal1( User user){
        user.setAccount("bbb");
    }

    public static void changeVal2( User user){
        user = new User();
        user.setAccount("ccc");
    }

    private static void m3(StringBuilder sb) {
    }

    private static void m2(StringBuilder sb) {
    }
}