package com.rh.beauty_thread.chapter06;

import java.util.concurrent.locks.LockSupport;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/2/8
 * \* Time: 16:31
 * \* Description:
 * \
 */
public class TestPark {
    public void testPark(){
        LockSupport.park(this);
    }

    public static void main(String[] args) {
        TestPark testPark = new TestPark();
        testPark.testPark();
    }
}