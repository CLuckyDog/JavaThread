package com.rh.bilibili.chapter06;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestAtomicRefence2ABA
 * @Description:
 * @author: pzj
 * @date: 2021/2/21 22:12
 */
@Slf4j(topic = "c.TestAtomicRefence2ABA")
public class TestAtomicRefenceABA {
    static AtomicReference<String> ref = new AtomicReference<>("A");
    public static void main(String[] args) {
        log.debug("main start ...");
        //获取值
        final String s = ref.get();
        other();
        sleep(1);
        //尝试改为C
        log.debug("change A->C {}",ref.compareAndSet(s,"C"));

    }

    private static void other(){
        new Thread(()->{
            //尝试改为B
            log.debug("change A->B {}",ref.compareAndSet(ref.get(),"B"));
        },"t1").start();
        sleep(0.5);
        new Thread(()->{
            //尝试改为A
            log.debug("change B->A {}",ref.compareAndSet(ref.get(),"A"));
        },"t2").start();
    }
}