package com.rh.chapter_two;

import java.util.concurrent.TimeUnit;

/**
 * 线程中 stop 方法的安全使用
 * 对 StopThreadUnsafe 类的优化
 */
public class StopThreadSafe {
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

        volatile boolean stopme=false;

        public void stopme(){
            stopme=true;
        }

        @Override
        public void run() {
            while (true){

                if(stopme){
                   System.out.println("exit by stop me !");
                   break;
                }

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
        new StopThreadSafe.ReadObjectThread().start();
        while (true){
            Thread t=new StopThreadSafe.ChangeObjectThread();
            t.start();
            TimeUnit.SECONDS.sleep(1);
//            t.stop();
            ((ChangeObjectThread) t).stopme();
        }
    }
}
