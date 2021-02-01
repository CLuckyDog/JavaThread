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

    static class Room{
        private int counter = 0;

        public void increment(){
            synchronized (this){
                counter++;
            }
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
}
