package com.rh.bilibili.chapter06;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import static com.rh.bilibili.utils.Sleeper.sleep;

@Slf4j(topic = "c.TestAtomicStempRef")
public class TestAtomicStempRef {
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A",0);
    public static void main(String[] args) {
        log.debug("main start ...");
        //获取值
        final String s = ref.getReference();
        final int stamp = ref.getStamp();
        log.debug("{}",stamp);
        other();
        sleep(1);
        //尝试改为C
        log.debug("{}",ref.getStamp());
        log.debug("change A->C {}",ref.compareAndSet(s,"C",stamp,stamp+1));

    }

    private static void other(){
        new Thread(()->{
            final int stamp = ref.getStamp();
            log.debug("{}",stamp);
            //尝试改为B
            log.debug("change A->B {}",ref.compareAndSet(ref.getReference(),"B",stamp,stamp+1));
        },"t1").start();
        sleep(0.5);
        new Thread(()->{
            final int stamp = ref.getStamp();
            log.debug("{}",stamp);
            //尝试改为A
            log.debug("change B->A {}",ref.compareAndSet(ref.getReference(),"A",stamp,stamp+1));
        },"t2").start();
    }
}
