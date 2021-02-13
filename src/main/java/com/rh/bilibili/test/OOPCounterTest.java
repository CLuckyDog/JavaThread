package com.rh.bilibili.test;

public class OOPCounterTest {

    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            room.increment();
        }, "t1");
        Thread t2 = new Thread(() -> {
            room.decrement();
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(room.getCounter());
    }

}

class Room{
    private int counter = 0;

    //        public void increment(){
//            synchronized (this){
//                counter++;
//            }
//        }
    //下面这个写法等价于上面那个写法，sync只能锁对象，可以锁实例对象this，也可以锁类对象X.class
    public synchronized void increment(){
        counter++;
    }

    public void decrement(){
        synchronized (this){
            counter--;
        }
    }

    public int getCounter(){
        synchronized (this){
            return counter;
        }
    }
}
