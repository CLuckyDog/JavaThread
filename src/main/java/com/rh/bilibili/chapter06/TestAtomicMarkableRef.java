package com.rh.bilibili.chapter06;   // 解决package

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestAtomicMarkableRef
 * @Description:
 * @author: pzj
 * @date: 2021/2/21 22:29
 */
@Slf4j(topic = "c.TestAtomicMarkableRef")
public class TestAtomicMarkableRef {
    public static void main(String[] args) {
        GarbageBag bag = new GarbageBag("装满了垃圾");
        //参数2 mark可以看做一个标记，表示垃圾袋满了
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag,true);

        log.debug("start ...");
        final GarbageBag prev = ref.getReference();
        log.debug(prev.toString());


        Thread t1 = new Thread(() -> {
            log.debug("start...");
            bag.setDesc("清空垃圾袋");
            ref.compareAndSet(bag,bag,true,false);
            log.debug(bag.toString());
        }, "保洁阿姨");
        t1.start();

        sleep(1);
        log.debug("想换一个新的垃圾袋");
        final boolean success = ref.compareAndSet(prev, new GarbageBag("新垃圾袋"), true, false);
        log.debug("换了么？"+success);
        log.debug(ref.getReference().toString());

    }
}

class GarbageBag {
    String desc;

    public GarbageBag(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return super.toString() + " " + desc;
    }
}