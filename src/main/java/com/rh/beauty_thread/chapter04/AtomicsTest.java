package com.rh.beauty_thread.chapter04;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/2/4
 * \* Time: 10:53
 * \* Description:
 * \
 */
public class AtomicsTest {
    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong();
        long andIncrement = atomicLong.getAndIncrement();
        LongAdder adder = new LongAdder();
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return 0;
            }
        },0);
    }
}