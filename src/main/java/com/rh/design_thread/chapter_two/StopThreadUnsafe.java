package com.rh.design_thread.chapter_two;

import java.util.concurrent.TimeUnit;

/**
 * 线程中 stop 方法的不安全使用
 */
public class StopThreadUnsafe {

    public static User u=new User();

    public static class User {
        private int id;
        private String name;

        public User() {
            this.id = 0;
            this.name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class ChangeObjectThread extends Thread {
        @Override
        public void run() {
            while (true){
                synchronized (u){
                    int v= (int) (System.currentTimeMillis()/1000);
                    u.setId(v);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    u.setName(String.valueOf(v));
                }
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true){
                synchronized (u){
                    if(u.getId()!=Integer.parseInt(u.getName())){
                        System.out.println(u.toString());
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();
        while (true){
            Thread t=new ChangeObjectThread();
            t.start();
            TimeUnit.SECONDS.sleep(1);
            t.stop();
        }
    }
}



